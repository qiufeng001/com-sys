package com.sys.domain.configuration;

import com.sys.domain.admin.UserRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackageClasses = { UserRepository.class })
public class DomainAutoConfig {

	public DomainAutoConfig() {
		System.out.println("init domain...");
	}
}