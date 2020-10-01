package com.sample.oauthsample.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sample.oauthsample.model.MyCustomUserDetails;
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
	return MyCustomUserDetails.build(usersOptional.get());
    }
}
