package com.sys.domain.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = { "com.sys.domain.admin", "com.sys.domain.base" })
public class DomainAutoConfig {

	public DomainAutoConfig() {
		System.out.println("init domain...");
	}
}