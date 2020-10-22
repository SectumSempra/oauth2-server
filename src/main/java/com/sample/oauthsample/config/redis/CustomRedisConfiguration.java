package com.sample.oauthsample.config.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author bilal.eraslan
 * @apiNote Uygulama redis connection
 */
@Configuration
public class CustomRedisConfiguration {

    /**
     * @apiNote Bean name for Jedis
     */
    public static final String JEDIS = "applicationJedis";

    @Autowired
    private RedisProperties redisProperties;

    private JedisPoolConfig getPoolConfig() {
	JedisPoolConfig config = new JedisPoolConfig();
	config.setMaxIdle(redisProperties.getJedisPoolMaxIdle());
	config.setMinIdle(redisProperties.getJedisPoolMinIdle());
	config.setMaxWaitMillis(redisProperties.getJedisPoolMaxWait());
	config.setMaxTotal(redisProperties.getJedisPoolMaxActive());
	return config;
    }

    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	redisStandaloneConfiguration.setDatabase(redisProperties.getRedisDB());
	redisStandaloneConfiguration.setHostName(redisProperties.getHost().trim());
	redisStandaloneConfiguration.setPort(redisProperties.getPort());
	if (!StringUtils.isEmpty(redisProperties.getPassword())) {
	    redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
	} else {
	    redisStandaloneConfiguration.setPassword(RedisPassword.of(""));
	}

	return redisStandaloneConfiguration;
    }

    private JedisClientConfiguration jedisClientConfiguration() {
	JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
	jedisClientConfigurationBuilder.usePooling().poolConfig(getPoolConfig());
	JedisClientConfiguration jCCB = jedisClientConfigurationBuilder.build();
	return jCCB;
    }

    @Bean(AppRedisUtils.REDIS_CONNECTION_FACTORY)
    public RedisConnectionFactory applicationRedisConnectionFactory() {
	return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
    }

    @Bean(JEDIS)
    public Jedis jedis() {
	JedisShardInfo shardInfo = new JedisShardInfo(redisProperties.getHost(), redisProperties.getPort());
	Jedis jedis = shardInfo.createResource();
	jedis.select(redisProperties.getRedisDB());
	return jedis;

    }

    @Bean(AppRedisUtils.redisTemplate)
    public RedisTemplate<String, String> redisTemplate() {
	RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
	redisTemplate.setConnectionFactory(applicationRedisConnectionFactory());
	redisTemplate.setDefaultSerializer(new StringRedisSerializer());
	redisTemplate.setValueSerializer(new StringRedisSerializer());
	redisTemplate.setKeySerializer(new StringRedisSerializer());
	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	return redisTemplate;
    }

    @Bean(AppRedisUtils.stringRedisTemplate)
    public StringRedisTemplate stringRedisTemplate() {
	StringRedisTemplate redisTemplate = new StringRedisTemplate();
	redisTemplate.setConnectionFactory(applicationRedisConnectionFactory());
	redisTemplate.setDefaultSerializer(new StringRedisSerializer());
	redisTemplate.setValueSerializer(new StringRedisSerializer());
	redisTemplate.setKeySerializer(new StringRedisSerializer());
	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	return redisTemplate;
    }

}
