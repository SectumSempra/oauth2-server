package com.sample.oauthsample.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    private String KEY_SEPERATOR = "#";

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	redisStandaloneConfiguration.setDatabase(redisProperties.database);
	redisStandaloneConfiguration.setHostName(redisProperties.host);
	redisStandaloneConfiguration.setPort(redisProperties.port);
	// redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.password));
	return redisStandaloneConfiguration;
    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration() {
	JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
	jedisClientConfigurationBuilder.connectTimeout(Duration.ofMinutes(30));
	JedisClientConfiguration jCCB = JedisClientConfiguration.builder().build();
	GenericObjectPoolConfig poolConfig = jCCB.getPoolConfig().get();
	poolConfig.setMaxIdle(redisProperties.jedisPoolMaxIdle);
	poolConfig.setMinIdle(redisProperties.jedisPoolMinIdle);
	return jCCB;
    }

    @Bean("jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
	return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
    }

    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	redisStandaloneConfiguration.setHostName(redisProperties.host);
	redisStandaloneConfiguration.setPort(redisProperties.port);
	redisStandaloneConfiguration.setDatabase(redisProperties.database);

	redisStandaloneConfiguration.setPassword(RedisPassword.of(""));

	JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
	GenericObjectPoolConfig config = getPoolConfig();
	jedisClientConfiguration.usePooling().poolConfig(config);
	JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
		jedisClientConfiguration.build());
	return jedisConFactory;
    }

    private GenericObjectPoolConfig getPoolConfig() {
	GenericObjectPoolConfig config = new GenericObjectPoolConfig();
	config.setMaxIdle(redisProperties.jedisPoolMaxIdle);
	config.setMinIdle(redisProperties.jedisPoolMinIdle);
	config.setMaxWaitMillis(redisProperties.jedisPoolMaxWait);
	config.setMaxTotal(redisProperties.jedisPoolMaxActive);
	return config;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
	RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
		.entryTtl(Duration.ofSeconds(600)).disableCachingNullValues();
	return cacheConfig;
    }

    @Primary
    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager() {
	RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory()).cacheDefaults(cacheConfiguration())
		.transactionAware().build();
	return rcm;
    }

    @Bean(name = "keyGenerator1")
    public KeyGenerator keyGenerator() {
	return new KeyGenerator() {
	    @Override
	    public Object generate(Object target, Method method, Object... params) {
		StringBuilder sb = new StringBuilder();
		sb.append(target.getClass().getSimpleName());
		sb.append(KEY_SEPERATOR);
		sb.append(method.getName());
		sb.append(KEY_SEPERATOR);
		for (Object param : params) {
		    sb.append(param.toString());
		    sb.append(KEY_SEPERATOR);
		}
		String str = sb.toString();
		String key = str.substring(0, str.length() - 1);
		System.out.println(key);

		return key;
	    }
	};
    }

    @Component
    @ConfigurationProperties
    @PropertySource("classpath:redis.properties")
    public static class RedisProperties {
	@Value("${redis.properties.database}")
	private int database;
	@Value("${redis.properties.host}")
	private String host;
	@Value("${redis.properties.password}")
	private String password;
	@Value("${redis.properties.port}")
	private int port;

	@Value("${redis.properties.jedis.pool.min-idle}")
	private int jedisPoolMinIdle;
	@Value("${redis.properties.jedis.pool.max-idle}")
	private int jedisPoolMaxIdle;
	@Value("${redis.properties.jedis.pool.max-active}")
	private int jedisPoolMaxActive;
	@Value("${redis.properties.jedis.pool.max-wait}")
	private int jedisPoolMaxWait;
    }

}
