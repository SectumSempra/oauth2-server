package com.sample.oauthsample.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {

    private String KEY_SEPERATOR = "#";

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();

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
        RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration())
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

}
