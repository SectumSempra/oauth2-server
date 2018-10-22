package com.sample.oauthsample.config;

import java.lang.reflect.Method;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {

    private String KEY_SEPERATOR = "#";

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisConnectionFactory redisConnectionFactory() {
	return new JedisConnectionFactory();

    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
	StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory());
	redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
	redisTemplate.setKeySerializer(new StringRedisSerializer());
	return redisTemplate;
    }

    @Primary
    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
	RedisCacheManager cacheManager = new RedisCacheManager(stringRedisTemplate());
	cacheManager.setDefaultExpiration(500);
	cacheManager.setUsePrefix(true);
	return cacheManager;
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

}
