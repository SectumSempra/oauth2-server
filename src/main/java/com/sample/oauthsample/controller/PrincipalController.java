package com.sample.oauthsample.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.oauthsample.model.Users;
import com.sample.oauthsample.services.UserService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrincipalController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Users getCurrentUser(Principal principal) {
	Optional<Users> usersOptional = userService.findByName(principal.getName());
	usersOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
	return usersOptional.map(Users::new).get();
    }

    @RequestMapping(value = "/principal")
    public Principal getPrincipal( Principal principal) {
	Authentication a = SecurityContextHolder.getContext().getAuthentication();
	System.out.println(" getPrincipal " + principal);

	return principal;
    }

}