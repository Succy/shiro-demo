package cn.succy.shiro.service;

public interface OAuthService {
    /**
     * 添加 access token
     * 在实现中，实际上会在缓存中添加两个缓存项：1：accessToken => username 2: username+password => accessToken
     */
    void addAccessToken(String username, String password, String accessToken);

    /**
     * 从缓存中获取accessToken
     */
    String getAccessTokenFromCache(String username, String password);

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

    /**
     * 检查clientId和clientSecret的合法性，二者属于与关系，必须满足clientId和clientSecret同时合法才会返回true
     */
    boolean checkClientIdAndClientSecretValid(String clientId, String clientSecret);
}
