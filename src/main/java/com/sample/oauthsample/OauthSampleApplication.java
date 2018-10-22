package com.sample.oauthsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("com.sample.oauthsample.repository")
@EntityScan("com.sample.oauthsample.model")
@EnableTransactionManagement
@SpringBootApplication
public class OauthSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthSampleApplication.class, args);
    }
}
