package com.sys.security.shiro;

import com.sys.security.cas.CasProperty;
import com.sys.security.redis.RedisProperty;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.Filter;
import java.util.*;

//@Configuration
public class ShiroConfigDev {

    private CasProperty casProperty;
    private RedisProperty redisProperty;
    private ShiroCasRealm shiroCasRealm;

    /**
     * 注册单点登出filter
     * 设置单点退出的拦截器,在登录的时候，客户端会去服务端进行认证，此时认证成功之后，
     * 服务端会将地址和ST返回给客户端，而在此时该拦截器会将session跟ST绑定在一起，
     * 如果访问退出的时候，此时服务端也会将服务地址和ST返回，此时的监听器会将所有的session全部变为失效。
     * <p>
     * SingleSignOutFilter需要配置在所有Filter之前，当Cas Client通过Cas Server登录成功，
     * Cas Server会携带生成的Service Ticket回调Cas Client，
     * 此时SingleSignOutFilter会将Service Ticket与当前的Session绑定在一起。
     * 当Cas Server在进行logout后回调Cas Client应用时也会携带该Service Ticket，
     * 此时Cas Client配置的SingleSignOutFilter将会使对应的Session失效，进而达到登出的目的。
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        bean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        bean.setFilter(new SingleSignOutFilter());
        //拦截所有的请求
        bean.addUrlPatterns("/*");
        bean.setEnabled(true);
        //设置优先级
        bean.setOrder(10);
        return bean;
    }

    /**
     * 设置shiro的拦截器工厂类
     * 在设置拦截器的时候，需要先执行cas的拦截器，再执行shiro的拦截器
     *
     * @param securityManager
     * @param casFilter
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager,
                                                            SysCasFilter casFilter) {

        String loginUrl = casProperty.getLoginUrl();
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 登录成功后要跳转的连接
//        shiroFilterFactoryBean.setSuccessUrl(casProperty.getLoginSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(casProperty.getUnauthorizedUrl());
        // 添加casFilter到shiroFilter中,注意，casFilter需要放到shiroFilter的前面
        Map<String, Filter> filters = new HashMap();
        filters.put("casFilter", casFilter);
//        filters.put("logout",logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    /**
     * 设置配置的触发的地方:用于设置shiro的拦截器，和将每一个拦截器的生命周期交给spring去管理
     * 注册DelegatingFilterProxy（Shiro）注册DelegatingFilterProxy(shiro)  是一个代理类，用于管理拦截器的生命周期，
     * 所有的请求都会拦截 ,在创建的时候，filter的执行会优先于bean的执行，所以需要使用该类先来管理bean
     * <p>
     * 该步只是将当前的的生命周期交给了spring管理，具体的管理还是需要下面的LifecycleBeanPostProcessor的对象去进行操作
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        //targetFilterLifecycle 指明作用于filter的所有生命周期
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        //拦截所有的请求
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    /**
     * 上面设置了声明周期，下面进行设置生命周期的自动化
     * 设置方法的自动初始化和销毁，init和destory方法被自动调用。
     * 注意，如果使用了该类，则不需要手动初始化方法和销毁方法，否则出错
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启注解声明:
     * 开启shiro aop 的注解支持，使用代理的方式，所以需要开启代码的支持
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        //设置代理方式，true是cglib的代理方式，false是普通的jdk代理方式
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启注解声明:
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    @Bean(name="redisManager")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperty.getHost());
        // 配置缓存过期时间
        redisManager.setTimeout(redisProperty.getMaxActive());
        redisManager.setJedisPool(redisPoolFactory());
        return redisManager;
    }

    /**
     * 4
     * 让某个实例的某个方法的返回值注入为Bean的实例
     * Spring静态注入
     *
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(CasProperty casProperty, RedisProperty redisProperty,
                                                                  ShiroCasRealm shiroCasRealm) {
        this.init(casProperty, redisProperty, shiroCasRealm);
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{getDefaultWebSecurityManager()});
        return factoryBean;
    }

    /**
     * 使用自定义redis缓存管理器
     * 解决redis中key为非字符串乱码问题
     * @return
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager myRedisCacheManager = new RedisCacheManager();
        //redis中针对不同用户缓存
        myRedisCacheManager.setPrincipalIdFieldName("account");
        myRedisCacheManager.setRedisManager(redisManager());
        return myRedisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setKeyPrefix("shiro:session:");
        return redisSessionDAO;
    }

    /**
     * shiro session的管理
     */
    @Bean(name = "redisSessionManager")
    public DefaultWebSessionManager redisSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        //会话超时时间，单位：毫秒
        sessionManager.setGlobalSessionTimeout(1800000);
        //当跳出SHIRO SERVLET时如ERROR-PAGE容器会为JSESSIONID重新分配值导致登录会话丢失
        sessionManager.setSessionIdCookie(shrioCookie());
        // 删除过期的session
        sessionManager.setDeleteInvalidSessions(true);
        // 去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 是否定时检查session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话,单位为毫秒
        sessionManager.setSessionValidationInterval(5000);
        List<SessionListener>  filters = new ArrayList<>();
        filters.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(filters);
        return sessionManager;
    }

    /**
     * cookie 属性设置
     */
    public SimpleCookie shrioCookie() {
        SimpleCookie cookie = new SimpleCookie("shiroCasCookie");
        //如果是单点登录，各个系统要设置相同的父域名public.com,否则会出现每进入一个子系统都会生成一个session，
        //也就是session没有实现共享，在退出后，子系统中用户还有残留！
        cookie.setDomain(casProperty.getDomain());
        //JSESSIONID的path为/用于多个系统共享JSESSIONID
        cookie.setPath("/");
        //浏览器中通过document.cookie可以获取cookie属性，设置了HttpOnly=true,在脚本中就不能得到cookie，可以避免cookie被盗用
        cookie.setHttpOnly(casProperty.isHttpOnly());
        /*maxAge=-1表示浏览器关闭时失效此Cookie*/
        cookie.setMaxAge(24 * 60 * 60);
        return cookie;
    }


    /**
     * @param
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {

        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        //用户授权/认证信息Cache, 采用EhCache 缓存
        dwsm.setCacheManager(redisCacheManager());
        // 指定 SubjectFactory
        dwsm.setSubjectFactory(new CasSubjectFactory());
        dwsm.setSessionManager(redisSessionManager());
        dwsm.setRealm(shiroCasRealm);
        return dwsm;
    }

    @Bean(name = "jedis")
    public Jedis jedisCommands() {
        return redisManager().getJedisPool().getResource();
    }

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperty.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperty.getMaxWaitMillis());
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisProperty.getUrl(),
                redisProperty.getPort(), redisProperty.getTimeout());
        return jedisPool;
    }

    /**
     * CAS过滤器
     *
     * @return
     */
    @Bean(name = "casFilter")
    public SysCasFilter getCasFilter() {
        SysCasFilter casFilter = new SysCasFilter();
        //自动注入拦截器的名称
        casFilter.setName("casFilter");
        //是否自动的将当前的拦截器进行注入
        casFilter.setEnabled(true);
        // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
        // 我们选择认证失败后重新登录
        casFilter.setFailureUrl(casProperty.getLoginUrl());
        return casFilter;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）,角色/权限信息由MyShiroCasRealm对象提供doGetAuthorizationInfo实现获取来的
     *
     * @param shiroFilterFactoryBean
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // authc：该过滤器下的页面必须登录后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // anon: 可以理解为不拦截
        // user: 登录了就不拦截
        // roles["admin"] 用户拥有admin角色
        // perms["permission1"] 用户拥有permission1权限
        // filter顺序按照定义顺序匹配，匹配到就验证，验证完毕结束。
        // url匹配通配符支持：? * **,分别表示匹配1个，匹配0-n个（不含子路径），匹配下级所有路径

        //1.shiro集成cas后，首先添加该规则
        /*filterChainDefinitionMap.put("/", "casFilter");
        //2.不拦截的请求 对静态资源设置匿名访问
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        // 退出 logout地址，shiro去清除session
        // 此处将logout页面设置为anon，而不是logout，因为logout被单点处理，而不需要再被shiro的logoutFilter进行拦截
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/login", "anon");
        //不需要登录拦截的接口
        filterChainDefinitionMap.put("/system/api/**","anon");

        //3.拦截的请求（从本地数据库获取或者从casserver获取(webservice,http等远程方式)，看你的角色权限配置在哪里）
        filterChainDefinitionMap.put("/user", "authc");

        //4.登录过的不拦截
        filterChainDefinitionMap.put("/**", "authc");*/
        filterChainDefinitionMap.put(casProperty.getCasFilterUrlPattern(), "casFilter");
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
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    private void init(CasProperty casProperty, RedisProperty redisProperty,
                      ShiroCasRealm shiroCasRealm) {
        this.casProperty = casProperty;
        this.redisProperty = redisProperty;
        this.shiroCasRealm = shiroCasRealm;

    }
}
