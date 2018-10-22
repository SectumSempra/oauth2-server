package com.sample.oauthsample.services;

import java.util.List;
import java.util.Optional;

import com.sample.oauthsample.model.Users;

public interface UserService {

    Users findById(Integer id);

    Optional<Users> findByName(String name);

    void saveUser(Users user);

    void updateUser(Users user);

    void deleteUserById(Integer id);

    void deleteUserByName(String name);

    List<Users> findAllUsers();

    boolean isUserExist(Users user);

}
