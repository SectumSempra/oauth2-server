package com.sample.oauthsample.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.sample.oauthsample.config.provider.MfaTokenGranter;
import com.sample.oauthsample.config.provider.PasswordTokenGranter;

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
    @Qualifier("bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	security.allowFormAuthenticationForClients();
	security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	clients.inMemory()/**/
		.withClient(CLIENT_ID)/**/
		.secret(bCryptPasswordEncoder.encode(SECRET_ID))/**/
		.accessTokenValiditySeconds(60 * 20)/**/
		.refreshTokenValiditySeconds(60 * 20)/**/
		.authorizedGrantTypes("password-sms", "mfa", "client_credentials", "password", "refresh_token")/**/
		.scopes(SCOPES)/**/
		.resourceIds(RESOURCE_ID)/**/
		.and()/**/
		.withClient("test")/**/
		.authorizedGrantTypes("authorization_code")/**/
		.authorities("ROLE_CLIENT")/**/
		.scopes(SCOPES)/**/
		.resourceIds(RESOURCE_ID)/**/
		.secret(bCryptPasswordEncoder.encode("test"))/**/
		.redirectUris("http://localhost:8484/client/login");/**/
    }

    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager)
		.userDetailsService(customUserDetailsService).tokenGranter(getalltokenGranters(endpoints))
		.reuseRefreshTokens(false);

    }

    private TokenGranter getalltokenGranters(final AuthorizationServerEndpointsConfigurer endpoints) {
	List<TokenGranter> granters = new ArrayList<>();
	granters.add(endpoints.getTokenGranter());
	granters.add(new PasswordTokenGranter(endpoints, authenticationManager));
	granters.add(new MfaTokenGranter(endpoints, authenticationManager));
	return new CompositeTokenGranter(granters);
    }

}
