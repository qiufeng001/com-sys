//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sys.core.redis;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class CustomRedisFactoryBean implements MethodInterceptor, FactoryBean<JedisCommands>, Serializable {

    private CustomJedisPool jedisPool;
    private CustomJedis jedis;
    private CustomJedisCommands proxy;
    private String host;
    private int port;

    public CustomRedisFactoryBean(String host, int port) {
        this.host = host;
        this.port = port;
        this.jedis = new CustomJedis(host, port);
        this.proxy = (CustomJedisCommands) (new ProxyFactory(CustomJedisCommands.class, this)).getProxy();
    }

    public CustomRedisFactoryBean(JedisPoolConfig jedisPoolConfig, String host, int port) {
        this.host = host;
        this.port = port;
        this.jedisPool = new CustomJedisPool(jedisPoolConfig, host, port);
        this.proxy = (CustomJedisCommands) (new ProxyFactory(CustomJedisCommands.class, this)).getProxy();
    }

    public CustomRedisFactoryBean(JedisPoolConfig jedisPoolConfig, String host, int port, int dbIndex) {
        this.host = host;
        this.port = port;
        this.jedisPool = new CustomJedisPool(jedisPoolConfig, host, port, dbIndex);
        this.proxy = (CustomJedisCommands) (new ProxyFactory(CustomJedisCommands.class, this)).getProxy();
    }

    CustomJedisCommands getJedis() {
        return this.proxy;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return this.jedisPool == null ? this.invokeInternal(invocation) : this.invokeInternalWithPool(invocation);
    }

    public synchronized Object invokeInternal(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.getMethod().invoke(this.jedis, invocation.getArguments());
        } catch (InvocationTargetException var6) {
            Throwable targetException = var6.getTargetException();
            if (targetException != null) {
                if (targetException instanceof JedisConnectionException) {
                    try {
                        this.jedis.disconnect();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }

                    this.jedis = new CustomJedis(this.host, this.port);
                }

                throw targetException;
            } else {
                throw var6;
            }
        }
    }

    public Object invokeInternalWithPool(MethodInvocation invocation) throws Throwable {
        CustomJedis jedisFromPool = this.jedisPool.getResource();
        Object result;

        try {
            result = invocation.getMethod().invoke(jedisFromPool, invocation.getArguments());
        } catch (InvocationTargetException var6) {
            Throwable targetException = var6.getTargetException();
            if (targetException != null) {
                if (targetException instanceof JedisConnectionException) {
                    this.returnBrokenJedis(jedisFromPool);
                } else {
                    this.returnJedis(jedisFromPool);
                }

                throw targetException;
            }

            this.returnJedis(jedisFromPool);
            throw var6;
        }

        this.returnJedis(jedisFromPool);
        return result;
    }

    void returnBrokenJedis(CustomJedis jedisFromPool) {
        try {
//            this.jedisPool.returnBrokenResource(jedisFromPool);
            jedisFromPool.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    void returnJedis(CustomJedis jedisFromPool) {
        try {
            jedisFromPool.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @Override
    public CustomJedisCommands getObject() throws Exception {
        return this.getJedis();
    }

    @Override
    public Class<CustomJedisCommands> getObjectType() {
        return CustomJedisCommands.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
