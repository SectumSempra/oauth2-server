package com.sample.oauthsample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.client.client-id}")
    private String CLIENT_ID;

    @Value("${security.oauth2.client.client-secret}")
    private String SECRET_ID;

    @Value("${security.oauth2.client.scope}")
    private String SCOPES;

    @Value("${security.oauth2.resource.id}")
    private String RESOURCE_ID;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	security.allowFormAuthenticationForClients();
	security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	clients.inMemory().withClient(CLIENT_ID).secret(SECRET_ID).accessTokenValiditySeconds(5000)
		.authorizedGrantTypes("password", "refresh_token").scopes(SCOPES).autoApprove(true)
		.resourceIds(RESOURCE_ID);
    }

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean(name = "authRedisTokenStore")
    public TokenStore tokenStore() {
	return new RedisTokenStore(connectionFactory);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
		.userDetailsService(customUserDetailsService);
    }

}
