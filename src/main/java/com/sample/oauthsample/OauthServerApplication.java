package com.sample.oauthsample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableJpaRepositories("com.sample.oauthsample.repository")
@EntityScan("com.sample.oauthsample.model")
@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class OauthServerApplication implements CommandLineRunner {

    @Bean
    public RestTemplate restTemplate() {
	return new RestTemplate();
    };

    public static void main(String[] args) {
	SpringApplication.run(OauthServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
