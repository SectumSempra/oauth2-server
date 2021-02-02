package com.sample.oauthsample.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sample.oauthsample.model.Users;
import com.sample.oauthsample.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	System.err.println("******DAOAuthenticationProvider*******");

	Optional<Users> usersOptional = usersRepository.findByName(username);
	usersOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
	Users user = usersOptional.get();

	List<GrantedAuthority> authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
		.collect(Collectors.toList());

	return User.builder().username(user.getName()).password(user.getPassword()).authorities(authorities).build();
    }
}
