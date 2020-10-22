package com.sample.oauthsample.config.redis;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.sample.oauthsample.config.AppConfig;

/**
 * @author bilal.eraslan
 * @apiNote Uygulamaya ait redis connection ile redis cache mananger ayari
 */
@Configuration
@EnableCaching
public class CustomRedisCacheManagerConfig extends CachingConfigurerSupport {

    @Autowired
    AppConfig appConfig;

    @Autowired
    @Qualifier(AppRedisUtils.REDIS_CONNECTION_FACTORY)
    private RedisConnectionFactory applicationRedisConnectionFactory;

    private final String PREFIX_CACHE_NAME = "XXX";

    private static final Integer DEFAULT_CACHE_DURATION_HOUR = 4;

    private static final Integer DEFAULT_CACHE_DURATION_HOUR_TXN = 4;

    private RedisCacheConfiguration defaultCacheConfig() {
	RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
		.computePrefixWith(cacheName -> getCacheNameWithPrefix(cacheName))
		.entryTtl(Duration.ofHours(DEFAULT_CACHE_DURATION_HOUR)).disableCachingNullValues();

	return cacheConfig;
    }

    /**
     * @param cacheName
     * @return
     */
    private String getCacheNameWithPrefix(String cacheName) {
	return PREFIX_CACHE_NAME + AppRedisUtils.KEY_SEPERATOR + cacheName + AppRedisUtils.KEY_SEPERATOR;
    }

    @Primary
    @Bean(name = AppRedisUtils.CacheBeans.CACHE_MANAGER)
    @Override
    public RedisCacheManager cacheManager() {
	RedisCacheManagerBuilder cacheManager = RedisCacheManagerBuilder
		.fromConnectionFactory(applicationRedisConnectionFactory);
	cacheManager.cacheDefaults(defaultCacheConfig());
	cacheManager.withInitialCacheConfigurations(getMappedCacheConfigurations());
	return cacheManager.build();
    }

    private Map<String, RedisCacheConfiguration> getMappedCacheConfigurations() {
	Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
	for (Entry<String, Long> entry : appConfig.getCaches().entrySet()) {
	    cacheConfigurations.put(entry.getKey(),
		    RedisCacheConfiguration.defaultCacheConfig()
			    .computePrefixWith(cacheName -> getCacheNameWithPrefix(cacheName))
			    .disableCachingNullValues().entryTtl(Duration.ofSeconds(entry.getValue())));
	}
	return cacheConfigurations;
    }

    private RedisCacheConfiguration defaultCacheConfigTXN() {
	RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
		.computePrefixWith(cacheName -> getCacheNameWithPrefix(cacheName))
		.entryTtl(Duration.ofHours(DEFAULT_CACHE_DURATION_HOUR_TXN)).disableCachingNullValues();
	return cacheConfig;
    }

    @Bean(name = AppRedisUtils.CacheBeans.CACHE_MANAGER_TXN)
    public RedisCacheManager cacheManagerTXN() {
	RedisCacheManagerBuilder cacheManager = RedisCacheManagerBuilder
		.fromConnectionFactory(applicationRedisConnectionFactory);
	cacheManager.cacheDefaults(defaultCacheConfigTXN()).transactionAware().build();
	return cacheManager.build();
    }

    @Bean(name = AppRedisUtils.CacheBeans.KEY_GENERATOR)
    @Override
    public KeyGenerator keyGenerator() {
	return new KeyGenerator() {
	    @Override
	    public Object generate(Object target, Method method, Object... params) {
		return getKey(target.getClass().getSimpleName(), method.getName());
	    }

	};
    }

    private Object getKey(String simpleNameClass, String methodName) {
	StringBuilder sb = new StringBuilder();
	sb.append(simpleNameClass);
	sb.append(AppRedisUtils.KEY_SEPERATOR);
	sb.append(methodName);
	return sb.toString();
    }

    @Override
    public CacheErrorHandler errorHandler() {
	return new CustomCacheErrorHandler();
    }

}
