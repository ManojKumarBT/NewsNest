package com.cts.newsnest.authservice.config;

import com.cts.newsnest.authservice.domain.User;

import java.util.Map;

public interface JWTTokenGenerator {

    Map<String, String> generateToken(User user);
}
