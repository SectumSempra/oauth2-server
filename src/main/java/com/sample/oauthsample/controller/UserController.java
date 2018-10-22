package com.sample.oauthsample.controller;

import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.oauthsample.model.Users;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserController {

    @RequestMapping(value = "/user/principal", method = RequestMethod.GET)
    public Principal getPrincipal(Principal principal);

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Users getCurrentUser(Principal principal);

    @RequestMapping(value = "/user/hello", method = RequestMethod.GET)
    public String hello();

    // -------------------Retrieve All
    // Users---------------------------------------------

    @RequestMapping(value = "/user/getAll", method = RequestMethod.GET)
    public ResponseEntity< ? > listAllUsers();

    // -------------------Retrieve Single
    // User------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity< ? > getUser(@PathVariable("id") Integer id);
    // -------------------Create a
    // User-------------------------------------------

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity< ? > createUser(@RequestBody Users user);

    // ------------------- Update a User
    // ------------------------------------------------

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity< ? > updateUser(@PathVariable("id") Integer id, @RequestBody Users user);
    // ------------------- Delete a
    // User-----------------------------------------

    @RequestMapping(value = "/user/delete-by-id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity< ? > deleteUser(@PathVariable("id") Integer id);

    // ------------------- Delete All Users-----------------------------
    @RequestMapping(value = "/user/delete-by-name/{name}", method = RequestMethod.DELETE)
    public ResponseEntity< ? > deleteUserByName(@PathVariable("name") String name);
    // ------------------- Delete All Users-----------------------------

}