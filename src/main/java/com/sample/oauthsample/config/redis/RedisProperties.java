package com.sample.oauthsample.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("classpath:redis.properties")
public class RedisProperties {
    @Value("${redis.properties.redisDB}")
    private int redisDB;
    @Value("${redis.properties.tokenDB}")
    private int tokenDB;
    @Value("${redis.properties.host}")
    private String host;
    @Value("${redis.properties.password}")
    private String password;
    @Value("${redis.properties.port}")
    private int port;

    @Value("${redis.properties.jedis-pool.min-idle}")
    private int jedisPoolMinIdle;
    @Value("${redis.properties.jedis-pool.max-idle}")
    private int jedisPoolMaxIdle;
    @Value("${redis.properties.jedis-pool.max-active}")
    private int jedisPoolMaxActive;
    @Value("${redis.properties.jedis-pool.max-wait}")
    private int jedisPoolMaxWait;

    public int getRedisDB() {
	return redisDB;
    }

    public void setRedisDB(int redisDB) {
	this.redisDB = redisDB;
    }

    public int getTokenDB() {
	return tokenDB;
    }

    public void setTokenDB(int tokenDB) {
	this.tokenDB = tokenDB;
    }

    public String getHost() {
	return host;
    }

    public void setHost(String host) {
	this.host = host;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public int getPort() {
	return port;
    }

    public void setPort(int port) {
	this.port = port;
    }

    public int getJedisPoolMinIdle() {
	return jedisPoolMinIdle;
    }

    public void setJedisPoolMinIdle(int jedisPoolMinIdle) {
	this.jedisPoolMinIdle = jedisPoolMinIdle;
    }

    public int getJedisPoolMaxIdle() {
	return jedisPoolMaxIdle;
    }

    public void setJedisPoolMaxIdle(int jedisPoolMaxIdle) {
	this.jedisPoolMaxIdle = jedisPoolMaxIdle;
    }

    public int getJedisPoolMaxActive() {
	return jedisPoolMaxActive;
    }

    public void setJedisPoolMaxActive(int jedisPoolMaxActive) {
	this.jedisPoolMaxActive = jedisPoolMaxActive;
    }

    public int getJedisPoolMaxWait() {
	return jedisPoolMaxWait;
    }

    public void setJedisPoolMaxWait(int jedisPoolMaxWait) {
	this.jedisPoolMaxWait = jedisPoolMaxWait;
    }

}
