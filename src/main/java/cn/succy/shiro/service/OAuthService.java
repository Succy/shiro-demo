package cn.succy.shiro.service;

public interface OAuthService {
    /**
     * 添加 access token
     */
    void addAccessToken(String accessToken, String username);


    /**
     * 验证access token是否有效
     */
    boolean checkAccessToken(String accessToken);

    /**
     * 通过accessToken获取用户名
     */
    String getUsernameByAccessToken(String accessToken);


    /**
     * access token 过期时间
     */
    long getExpireIn();


    /**
     * 检查ClientId合法性
     */
    boolean checkClientId(String clientId);

    /**
     * 检查clientSecret合法性
     */
    boolean checkClientSecret(String clientSecret);
}
