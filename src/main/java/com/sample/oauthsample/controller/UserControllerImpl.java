package com.sample.oauthsample.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.oauthsample.model.Role;
import com.sample.oauthsample.model.Users;
import com.sample.oauthsample.repository.RoleRepository;
import com.sample.oauthsample.services.UserService;

@RestController
public class UserControllerImpl implements UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String hello() {
	return "Hello Auth Server ";
    }

    @Override
    public ResponseEntity<?> listAllUsers() {
	List<Users> list = userService.findAllUsers();
	return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getUser(Integer id) {
	Users s = userService.findById(id);
	return new ResponseEntity<>(s, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> createUser(@RequestBody Users user) {
	Integer count = userService.findAllUsers().size();
	user.setActive(1);
	user.setId(++count);
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

	Role r = roleRepository.findByRole("STANDARD");

	List<Role> ll = Arrays.asList(r);
	user.setRoles(new HashSet<>(ll));
	userService.saveUser(user);
	return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> updateUser(Integer id, @RequestBody Users user) {
	Users old = userService.findById(id);
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	userService.updateUser(user);
	return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> deleteUser(Integer id) {
	userService.deleteUserById(id);
	return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUserByName(String name) {
	userService.deleteUserByName(name);
	return new ResponseEntity<>(name, HttpStatus.OK);
    }

}
