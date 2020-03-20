package com.sys.security.shiro;

import com.sys.security.cas.CasProperty;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class ShiroRealmConfig {

    @Bean(name = "shiroCasRealm")
    public ShiroCasRealm shiroCasRealm(EhCacheManager cacheManager, CasProperty casProperty) {
        ShiroCasRealm realm = new ShiroCasRealm();
//        realm.setAuthenticationCachingEnabled(false);
        realm.setCacheManager(cacheManager);
        realm.setCasServerUrlPrefix(casProperty.getCasServerUrlPrefix());
        // 客户端回调地址
        realm.setCasService(casProperty.getLoginSuccessUrl());
        return realm;
    }

    @Bean(name = "securityManager")
    public SecurityManager getDefaultWebSecurityManager(ShiroCasRealm shiroCasRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(shiroCasRealm);
        // 自定义缓存实现 使用redis，可选开始
       /* if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            dwsm.setCacheManager(rediscacheManager());
        } else {
            dwsm.setCacheManager(ehCacheManager());
        }*/
        dwsm.setCacheManager(ehCacheManager());
        // 自定义缓存实现 使用redis，可选结束

        // 用户授权/认证信息Cache, 采用EhCache 缓存
        dwsm.setCacheManager(ehCacheManager());
        // 指定 SubjectFactory
        dwsm.setSubjectFactory(new CasSubjectFactory());
        return dwsm;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManager(CacheManager.create());
        return em;
    }
}
