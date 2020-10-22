package com.sample.oauthsample.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app-config")
public class AppConfig {

    private Map<String, Long> caches;

    public Map<String, Long> getCaches() {
	return caches;
    }

    public void setCaches(Map<String, Long> caches) {
	this.caches = caches;
    }

}
