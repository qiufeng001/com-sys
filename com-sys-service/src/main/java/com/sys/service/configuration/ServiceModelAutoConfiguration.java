package com.sys.service.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * ServiceModelAutoConfiguration
 *
 * @author zhong.h
 * @date 2019/10/31
 */

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceModelAutoConfiguration implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public ServiceModelAutoConfiguration() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() throws Exception {

    }



}