package com.sample.oauthsample.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CustomCacheErrorHandler implements CacheErrorHandler {

    Logger logger = LoggerFactory.getLogger(CustomCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
	logger.info("Error happen to Get from cache");

    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
	logger.info("Error happen to Put to cache");

    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
	logger.info("Error happen to Evict to cache");
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
	logger.info("Error happen to Clear to cache");

    }

}
