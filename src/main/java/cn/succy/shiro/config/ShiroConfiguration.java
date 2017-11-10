package cn.succy.shiro.config;

import cn.succy.shiro.cache.SpringCacheManagerWrapper;
import cn.succy.shiro.realm.UserRealm;
import cn.succy.shiro.util.Constants;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.LinkedHashMap;

/**
 * Created by succy on 17-11-9.
 *
 * @author Succy
 */
@Configuration
@EnableCaching
public class ShiroConfiguration {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/accessToken", "anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("userRealm") AuthorizingRealm realm, SpringCacheManagerWrapper cacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setCacheManager(cacheManager);
        return manager;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        EhCacheCacheManager ehcacheManager = new EhCacheCacheManager();
        ehcacheManager.setCacheManager(bean.getObject());
        return ehcacheManager;
    }

    @Bean(name = "ehCacheManagerFactoryBean")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource("ehcache/ehcache.xml"));
        bean.setShared(true);
        return bean;
    }


    @Bean(name = "cacheManager")
    public SpringCacheManagerWrapper getCacheManager(EhCacheCacheManager ehCacheManager) {
        SpringCacheManagerWrapper managerWrapper = new SpringCacheManagerWrapper();
        managerWrapper.setCacheManager(ehCacheManager);
        return managerWrapper;
    }

    @Bean("credentialsMatcher")
    public CredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Constants.HASHALGORITHM_NAME);
        credentialsMatcher.setHashIterations(Constants.HASH_ITERATIONS);
        return credentialsMatcher;
    }

    @Bean(name = "userRealm")
    public UserRealm userRealm(@Qualifier("cacheManager") SpringCacheManagerWrapper cacheManager) {
        UserRealm realm = new UserRealm();
        realm.setCacheManager(cacheManager);
        realm.setCredentialsMatcher(getCredentialsMatcher());
        return realm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
