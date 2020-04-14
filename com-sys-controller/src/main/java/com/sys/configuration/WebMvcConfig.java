package com.sys.configuration;

import com.sys.core.configuration.AbstractWebMvcConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Servlet;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurerAdapter.class})
@Order(Ordered.HIGHEST_PRECEDENCE )
//@EnableAspectJAutoProxy // 启动自动代理
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class WebMvcConfig extends AbstractWebMvcConfig {
   
	public WebMvcConfig(){
		System.out.println("init web...");
	}

	/*@Bean
	public FilterRegistrationBean authenticationFilter(CasProperty casProperty) {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new PlatFormFilter(casProperty));
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		filterRegistration.addInitParameter("useSession", "true");
		filterRegistration.addInitParameter("redirectAfterValidation","true");
		return filterRegistration;
	}*/

/*	@Bean
	public CustomCasFilter getCasFilter() {
		CustomCasFilter casFilter = new CustomCasFilter();
		casFilter.setName("casFilter");
		casFilter.setEnabled(true);
		// 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
		casFilter.setFailureUrl(casProperty.getLoginUrl());// 我们选择认证失败后再打开登录页面
		casFilter.setSuccessUrl(casProperty.getShiroServerUrlPrefix() + casProperty.getCasFilterUrlPattern());
		return casFilter;
	}*/
}
