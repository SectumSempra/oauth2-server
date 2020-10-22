package com.sample.oauthsample.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class TokenRedisConfig {

    public static final String TOKEN_REDIS_CONNECTION_FACTORY = "tokenRedisConnectionFactory";

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
	redisStandaloneConfiguration.setDatabase(redisProperties.getTokenDB());
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

    @Bean(TOKEN_REDIS_CONNECTION_FACTORY)
    public RedisConnectionFactory tokenRedisConnectionFactory() {
	return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
    }

    @Bean("tokenStore")
    public TokenStore tokenStore(@Qualifier(TOKEN_REDIS_CONNECTION_FACTORY) RedisConnectionFactory cf) {
	return new RedisTokenStore(cf);
    }

}
