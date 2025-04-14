package com.cts.newsnest.authservice.service;

import com.cts.newsnest.authservice.domain.CompleteUser;
import com.cts.newsnest.authservice.domain.User;
import com.cts.newsnest.authservice.exception.UserAlreadyExistException;
import com.cts.newsnest.authservice.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface UserService {

//    User findByIdAndPassword(String email, String password) throws UserNotFoundException;

    public String registerUser(CompleteUser completeUser) throws UserAlreadyExistException;

    public Map<String, String> loginUser(User user) throws UserNotFoundException;

    public void updateUser(String value) throws JsonProcessingException;

    public void deleteUser(String value) throws JsonProcessingException;
}
