package com.sys.core.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.util.Pool;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;


public class CustomJedis extends Jedis implements CustomJedisCommands, Serializable {
    protected Pool<CustomJedis> dataSource = null;

    public CustomJedis() {
    }

    public CustomJedis(String host) {
        super(host);
    }

    public CustomJedis(String host, int port) {
        super(host, port);
    }

    public CustomJedis(String host, int port, int timeout) {
        super(host, port, timeout);
    }

    public CustomJedis(String host, int port, int connectionTimeout, int soTimeout) {
        super(host, port, connectionTimeout, soTimeout);
    }

    public CustomJedis(JedisShardInfo shardInfo) {
        super(shardInfo);
    }

    public CustomJedis(URI uri) {
        super(uri);
    }

    public CustomJedis(URI uri, int timeout) {
        super(uri, timeout);
    }

    public CustomJedis(URI uri, int connectionTimeout, int soTimeout) {
        super(uri, connectionTimeout, soTimeout);
    }

    public void setWxsDataSource(Pool<CustomJedis> jedisPool) {
        this.dataSource = jedisPool;
    }

    @Override
    public String set(Long key, String value) {
        return super.set(key.toString(), value);
    }

    @Override
    public String get(Long key) {
        return super.get(key.toString());
    }

    @Override
    public Boolean exists(Long key) {
        return super.exists(key.toString());
    }

    @Override
    public String hget(Long key, String field) {
        return super.hget(key.toString(), field);
    }

    @Override
    public Long hset(String key, String field, Long value) {
        return super.hset(key, field, value.toString());
    }

    @Override
    public Long hset(Long key, String field, String value) {
        return super.hset(key.toString(), field, value);
    }

    @Override
    public Long hset(Long key, String field, Long value) {
        return super.hset(key.toString(), field, value.toString());
    }

    @Override
    public Long hdel(Long key, String... field) {
        return super.hdel(key.toString(), field);
    }

    @Override
    public Long del(Long key) {
        return super.del(key.toString());
    }

    @Override
    public String hmset(Long key, Map<String, String> hash) {
        return super.hmset(key.toString(), hash);
    }

    @Override
    public Long setnx(String key, Long value) {
        return super.setnx(key, value.toString());
    }
}