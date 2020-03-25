package com.sys.security.shiro;

import com.sys.controller.configuration.PlatFormFilter;
import com.sys.security.cas.CasProperty;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.*;

/**
 * shiro权限中心
 *
 * @author z.h
 */
@Configuration
public class ShiroConfig {

    /**
     * 注册单点登出的listener
     * 优先级需要高于Cas的Filter
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SingleSignOutHttpSessionListener());
        bean.setEnabled(true);
        return bean;
    }

    /**
     * 登出过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
    /*    FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        bean.setFilter(new SingleSignOutFilter());
        bean.addUrlPatterns("/");
        bean.setEnabled(true);
        return bean;*/
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistrationBean.addInitParameter("targetFilterLifecycle", "true");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
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
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @author SHANHY
     * @create 2016年1月14日
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean, CasProperty casProperty) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put(casProperty.getShiroFilterUrlPattern(), "casFilter");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/upload/**", "anon");
        filterChainDefinitionMap.put("/files/**", "anon");
        filterChainDefinitionMap.put("/blog", "anon");
        filterChainDefinitionMap.put("/blog/open/**", "anon");
//        filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * CAS过滤器
     *
     */
    @Bean(name = "casFilter")
    public CasFilter getCasFilter(CasProperty casProperty) {
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
        casFilter.setFailureUrl(casProperty.getLoginUrl());// 我们选择认证失败后再打开登录页面
        casFilter.setSuccessUrl(casProperty.getShiroServerUrlPrefix() + casProperty.getShiroFilterUrlPattern());
        return casFilter;
    }

    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager, CasFilter casFilter,
                                                            CasProperty casProperty) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(casProperty.getLoginUrl());
        // 登录成功后要跳转的连接
//    shiroFilterFactoryBean.setSuccessUrl(CasProp.loginSuccessUrl);
        shiroFilterFactoryBean.setUnauthorizedUrl(casProperty.getShiroServerUrlPrefix() + casProperty.getUnauthorizedUrl());
        // 添加casFilter到shiroFilter中
        Map<String, Filter> filters = new HashMap<>();
        filters.put("casFilter", casFilter);
        // 可以增加自定义的filter
        filters.put("platFormFilter", new PlatFormFilter());
        shiroFilterFactoryBean.getFilters().putAll(filters);

        loadShiroFilterChain(shiroFilterFactoryBean, casProperty);
        return shiroFilterFactoryBean;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManager(CacheManager.create());
        return em;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

   /* *//**
     * 配置shiro redisManager，可选
     *
     * @return
     *//*
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置缓存过期时间
        //redisManager.setTimeout(1800);
        redisManager.setPassword(password);
        return redisManager;
    }*/

    /**
     * cacheManager 缓存 redis实现，可选
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
  /*  @Bean
    public RedisCacheManager rediscacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
*/

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis，可选
     * 使用的是shiro-redis开源插件
     */
   /* @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }*/

    //可选
   /* @Bean
    public SessionDAO sessionDAO() {
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            return redisSessionDAO();
        } else {
            return new MemorySessionDAO();
        }
    }
*/
    /**
     * shiro session的管理，可选
     */
   /* @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
        sessionManager.setSessionDAO(sessionDAO());
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new BDSessionListener());
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }*/

}
