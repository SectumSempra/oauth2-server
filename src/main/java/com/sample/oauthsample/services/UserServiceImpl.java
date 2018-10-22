package com.sample.oauthsample.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.oauthsample.model.Users;
import com.sample.oauthsample.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users findById(Integer id) {
        return usersRepository.findById(id).get();

    }

    @Override
    public Optional<Users> findByName(String name) {
        return usersRepository.findByName(name);

    }

    @Override
    public void saveUser(Users user) {
        usersRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(Users user) {
        usersRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        usersRepository.delete(usersRepository.findById(id).get());

    }

    @Override
    public boolean isUserExist(Users user) {
        return findByName(user.getName()) != null;
    }

    @Override
    public void deleteUserByName(String name) {
        usersRepository.deleteByName(name);
    }

}
