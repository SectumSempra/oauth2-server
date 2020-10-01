package com.sample.oauthsample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

//    private String KEY_SEPERATOR = "#";
//
//    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
//	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//	redisStandaloneConfiguration.setDatabase(redisProperties.springDB);
//	redisStandaloneConfiguration.setHostName(redisProperties.host.trim());
//	redisStandaloneConfiguration.setPort(redisProperties.port);
//	// redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.password));
//	return redisStandaloneConfiguration;
//    }
//
//    private JedisClientConfiguration jedisClientConfiguration() {
//	JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
//	jedisClientConfigurationBuilder.connectTimeout(Duration.ofMinutes(30));
//	jedisClientConfigurationBuilder.usePooling().poolConfig(getPoolConfig());
//	JedisClientConfiguration jCCB = jedisClientConfigurationBuilder.build();
//	return jCCB;
//    }
//
//    @Bean("jedisConnectionFactory")
//    public JedisConnectionFactory jedisConnectionFactory() {
//	return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
//    }

    @Bean(name = "redisConnectionFactory")
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	redisStandaloneConfiguration.setHostName(redisProperties.host.trim());
	redisStandaloneConfiguration.setPort(redisProperties.port);
	redisStandaloneConfiguration.setDatabase(redisProperties.redisDB);
	redisStandaloneConfiguration.setPassword(RedisPassword.of(""));

	JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
	jedisClientConfiguration.usePooling().poolConfig(getPoolConfig());
	JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
		jedisClientConfiguration.build());
	return jedisConFactory;
    }

    private JedisPoolConfig getPoolConfig() {
	JedisPoolConfig config = new JedisPoolConfig();
	config.setMaxIdle(redisProperties.jedisPoolMaxIdle);
	config.setMinIdle(redisProperties.jedisPoolMinIdle);
	config.setMaxWaitMillis(redisProperties.jedisPoolMaxWait);
	config.setMaxTotal(redisProperties.jedisPoolMaxActive);
	return config;
    }

    @Bean
    @Primary
    public RedisCacheConfiguration defaultCacheConfig(ObjectMapper objectMapper) {
	return RedisCacheConfiguration.defaultCacheConfig()
		.serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(
			SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
    }

    @Primary
    @Bean("redisTemplate")
    public RedisTemplate<String, String> redisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory cf) {
	RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
	redisTemplate.setConnectionFactory(cf);
	redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	redisTemplate.setStringSerializer(new StringRedisSerializer());
	return redisTemplate;
    }

    @Primary
    @Bean("redisTemplateObject")
    public RedisTemplate<Object, Object> redisTemplateObject(
	    @Qualifier("redisConnectionFactory") RedisConnectionFactory cf) {
	RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
	redisTemplate.setConnectionFactory(cf);
	redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	redisTemplate.setStringSerializer(new StringRedisSerializer());
	return redisTemplate;
    }

//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//	RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//		.entryTtl(Duration.ofSeconds(600)).disableCachingNullValues();
//	return cacheConfig;
//    }
//
//    @Primary
//    @Bean(name = "cacheManager")
//    public RedisCacheManager cacheManager() {
//	RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory()).cacheDefaults(cacheConfiguration())
//		.transactionAware().build();
//	return rcm;
//    }
//
//    @Bean(name = "keyGenerator1")
//    public KeyGenerator keyGenerator() {
//	return new KeyGenerator() {
//	    @Override
//	    public Object generate(Object target, Method method, Object... params) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(target.getClass().getSimpleName());
//		sb.append(KEY_SEPERATOR);
//		sb.append(method.getName());
//		sb.append(KEY_SEPERATOR);
//		for (Object param : params) {
//		    sb.append(param.toString());
//		    sb.append(KEY_SEPERATOR);
//		}
//		String str = sb.toString();
//		String key = str.substring(0, str.length() - 1);
//		System.out.println(key);
//
//		return key;
//	    }
//	};
//    }

    @Component
    @ConfigurationProperties
    @PropertySource("classpath:redis.properties")
    public static class RedisProperties {
	@Value("${redis.properties.redisDB}")
	private int redisDB;
	@Value("${redis.properties.springDB}")
	private int springDB;
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
