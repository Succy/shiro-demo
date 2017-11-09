package cn.succy.shiro.service.impl;

import cn.succy.shiro.service.OAuthService;
import org.springframework.stereotype.Service;

/**
 * @author Succy
 * @date 2017-11-08 19:15
 **/
@Service
public class OAuthServiceImpl implements OAuthService {

    @Override
    public void addAccessToken(String accessToken, String username) {

    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return false;
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return null;
    }

    @Override
    public long getExpireIn() {
        return 0;
    }

    @Override
    public boolean checkClientId(String clientId) {
        return false;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return false;
    }
}
