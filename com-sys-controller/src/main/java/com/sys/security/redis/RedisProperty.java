package com.sys.security.redis;

import com.sys.core.configuration.AbstractCasProperty;
import com.sys.core.configuration.AbstractRedisProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * cas静态文件加载中心
 *
 * @author z.h
 */
@Component
@ConfigurationProperties(prefix = "jedis")
@PropertySource(value = { "classpath:conf/redis.properties" })
public class RedisProperty extends AbstractRedisProperty {
}
