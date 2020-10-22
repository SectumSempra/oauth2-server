package com.sample.oauthsample.config.redis;

/**
 * @author bilal.eraslan
 * @apiNote Uygulama ya ait redis cache utils
 */
public class AppRedisUtils {

    /**
     * @apiNote Bean name for Application Redis Factroy
     */
    public static final String REDIS_CONNECTION_FACTORY = "redisConnectionFactory";

    /**
     * @apiNote Bean names for RedisTemplate&lt;String, String&gt;
     */
    public static final String redisTemplate = "redisTemplate";

    /**
     * @apiNote Bean name for stringRedisTemplate
     */
    public static final String stringRedisTemplate = "stringRedisTemplate";

    /**
     * @apiNote cache KEY_SEPERATOR
     */
    public static final String KEY_SEPERATOR = "::";

    public static class CacheBeans {
	/**
	 * @apiNote Bean name for Application Cache Manager
	 */
	public static final String CACHE_MANAGER = "cacheManager";
	/**
	 * @apiNote Bean name for Application Cache Manager with Transaction
	 */
	public static final String CACHE_MANAGER_TXN = "cacheManagerTXN";
	/**
	 * @apiNote Bean name for Application Redis Cache Key Generator
	 */
	public static final String KEY_GENERATOR = "keyGenerator";

    }

}
