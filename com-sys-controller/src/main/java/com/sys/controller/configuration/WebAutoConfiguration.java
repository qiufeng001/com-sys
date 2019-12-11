package com.sys.controller.configuration;

import com.sys.service.configuration.ServiceAutoConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration
 *
 * @author zhong.h
 * @date 2019/10/31
 */
@Configuration
@AutoConfigureAfter(ServiceAutoConfig.class)
public class WebAutoConfiguration {

}