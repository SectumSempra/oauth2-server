package com.sample.oauthsample.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MyCustomUserDetails implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = -4058402420865235575L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public MyCustomUserDetails() {
    }

    public MyCustomUserDetails(Long id, String username, String email, String password,
	    Collection<? extends GrantedAuthority> authorities) {
	this.id = id;
	this.username = username;
	this.email = email;
	this.password = password;
	this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return authorities;
    }

    @Override
    public String getPassword() {
	return password;
    }

    @Override
    public String getUsername() {
	return username;
    }

    public String getEmail() {
	return email;
    }

    public Long getId() {
	return id;
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }

    public static MyCustomUserDetails build(Users user) {
	List<GrantedAuthority> authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
		.collect(Collectors.toList());

	return new MyCustomUserDetails(user.getId(), user.getName(), user.getEmail(), user.getPassword(), authorities);
    }
}
