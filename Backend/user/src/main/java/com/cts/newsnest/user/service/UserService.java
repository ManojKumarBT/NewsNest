package com.cts.newsnest.user.service;

import com.cts.newsnest.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserService {

//    public User registerUser(User user);

    public List<User> getAllUsers();

    public User getUserById(int id);

    public User getUserByEmail(String email);

    public String updateUser(User user);

    public String deleteUserById(int id);

    public String deleteUserByEmailId(String email);

    public String deleteAllUsers();

    public void registerUser(String value) throws JsonProcessingException;
}
