package com.sys.service.configuration;

import com.sys.domain.configuration.DomainAutoConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ServiceConfiguration
 *
 * @author zhong.h
 * @date 2019/10/31
 */
@Configuration
@ComponentScan(basePackages = {"com.sys.service"})
public class ServiceAutoConfig {

    public ServiceAutoConfig() {
        System.out.println("init service config...");
    }
}
