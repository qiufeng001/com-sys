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

}
