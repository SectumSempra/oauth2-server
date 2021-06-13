package com.sample.oauthsample.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sample.oauthsample.config.provider.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/h2-db/**");
    }

    @Bean("bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
    }

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	authenticationProvider.setUserDetailsService(customUserDetailsService);
	authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
	authenticationProvider.setHideUserNotFoundExceptions(false);
	return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(customAuthenticationProvider);
	auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**/

    /************ Cors ***********/
    private final List<String> allowedOrigins = Arrays.asList("*");

    private final List<String> allowedHeaders = Arrays.asList("Content-Type", "Access-Control-Allow-Origin",
	    "Authorization");

    private final List<String> allowedMethods = Arrays.asList("POST", "GET", "OPTIONS", "PUT");

    @Bean
    public CorsFilter corsFilter() {
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	CorsConfiguration config = new CorsConfiguration();
	config.setAllowedOrigins(allowedOrigins);
	config.setAllowCredentials(true);
	config.setAllowedMethods(allowedMethods);
	config.setAllowedHeaders(allowedHeaders);
	source.registerCorsConfiguration("/**", config);
	return new CorsFilter(source);
    }
    /**/

    @Override
    public void configure(HttpSecurity http) throws Exception {
	http.addFilterBefore(corsFilter(), ChannelProcessingFilter.class);
	http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().antMatchers("/oauth/**")
		.permitAll().antMatchers("/h2-db/**").permitAll().antMatchers("/api/**").authenticated();

    }

}
