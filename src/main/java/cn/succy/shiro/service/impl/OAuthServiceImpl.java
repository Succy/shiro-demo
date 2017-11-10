package cn.succy.shiro.service.impl;

import cn.succy.shiro.service.ClientService;
import cn.succy.shiro.service.OAuthService;
import cn.succy.shiro.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

/**
 * @author Succy
 * @date 2017-11-08 19:15
 **/
@Service
@Slf4j
public class OAuthServiceImpl implements OAuthService {
    @Autowired
    private ClientService clientService;
    private Cache cache;

    /**
     * 使用Spring包装过的CacheManager和Spring 提供的Cache
     *
     * @param cacheManager 缓存管理器
     */
    @Autowired
    public OAuthServiceImpl(EhCacheCacheManager cacheManager) {
        this.cache = cacheManager.getCache("tokenCache");
    }

    @Override
    public void addAccessToken(String username, String password, String accessToken) {
        cache.put(accessToken, username);
        String key = new SimpleHash(Constants.HASHALGORITHM_NAME,
                username,
                ByteSource.Util.bytes(password),
                Constants.HASH_ITERATIONS).toHex();
        cache.put(key, accessToken);
    }

    @Override
    public String getAccessTokenFromCache(String username, String password) {
        String key = new SimpleHash(Constants.HASHALGORITHM_NAME,
                username,
                ByteSource.Util.bytes(password),
                Constants.HASH_ITERATIONS).toHex();
        return  cache.get(key, String.class);
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return cache.get(accessToken) != null;
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return cache.get(accessToken, String.class);
    }

    @Override
    public long getExpireIn() {
        return 3600L;
    }

    @Override
    public boolean checkClientId(String clientId) {
        return clientService.findByClientId(clientId) != null;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return clientService.findByClientSecret(clientSecret) != null;
    }

    @Override
    public boolean checkClientIdAndClientSecretValid(String clientId, String clientSecret) {
        return clientService.findByClientIdAndClientSecret(clientId, clientSecret) != null;
    }
}
