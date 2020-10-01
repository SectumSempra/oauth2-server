package com.sample.oauthsample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceApplication extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.id}")
    private String RESOURCE_ID;

    @Autowired
    public TokenStore redisTokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	resources.tokenStore(redisTokenStore).resourceId(RESOURCE_ID);

    }

}
