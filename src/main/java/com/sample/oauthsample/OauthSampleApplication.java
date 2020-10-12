package com.sample.oauthsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("com.sample.oauthsample.repository")
@EntityScan("com.sample.oauthsample.model")
@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class OauthSampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(OauthSampleApplication.class, args);
	}

	@Autowired
	@Qualifier("redisTemplateV2")
	RedisTemplate<String, String> redisTemplateV2;

	@Override
	public void run(String... args) throws Exception {
		redisTemplateV2.opsForValue().set("A", "AAAAAAAA");

	}
}
