package cn.succy.shiro.controller;

import cn.succy.shiro.service.OAuthService;
import cn.succy.shiro.service.UserService;
import cn.succy.shiro.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Succy
 * @date 2017-11-08 19:42
 **/
@RestController
@Slf4j
public class AccessTokenController {
    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private UserService userService;

    @PostMapping("/accessToken")
    public HttpEntity<?> token(HttpServletRequest request) {
        try {
            // 构造OAuth请求
            OAuthTokenRequest oAuthTokenRequest = new OAuthTokenRequest(request);
            // 校验客户端的client_id和client_secret是否正确
            if (!oAuthService.checkClientId(oAuthTokenRequest.getClientId()) || !oAuthService.checkClientSecret(oAuthTokenRequest.getClientSecret())) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)
                        .buildJSONMessage();
                return ResponseEntity.status(HttpStatus.valueOf(response.getResponseStatus()))
                        .body(response.getBody());
            }
            // 检验grant_type
            if (!oAuthTokenRequest.getGrantType().equals(GrantType.PASSWORD.toString())) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription(Constants.INVALID_GRANT_TYPE)
                        .buildJSONMessage();
                log.error("{}", response.getBody());
                return ResponseEntity.status(HttpStatus.valueOf(response.getResponseStatus()))
                        .body(response.getBody());
            }
            // 验证username和password
            String username = oAuthTokenRequest.getUsername();
            String password = oAuthTokenRequest.getPassword();
            // 使用shiro进行验证
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                SecurityUtils.getSubject().login(token);
            } catch (Exception ex) {
                // 用户名密码错误，返回给调用客户端
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_REQUEST)
                        .setErrorDescription(Constants.INVALID_USERNAME_OR_PASSWORD)
                        .buildJSONMessage();
                log.error("invalid username or password; msg:{}", response.getBody());
                return ResponseEntity.status(HttpStatus.valueOf(response.getResponseStatus()))
                        .body(response.getBody());
            }

            // 生成accessToken
            OAuthIssuer oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = oAuthIssuer.accessToken();
            oAuthService.addAccessToken(accessToken, username);

            //生成OAuth token响应
            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(String.valueOf(oAuthService.getExpireIn()))
                    .buildJSONMessage();

            return ResponseEntity.status(HttpStatus.valueOf(response.getResponseStatus()))
                    .body(response.getBody());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务端异常,请稍后重试");
        }
    }
}
