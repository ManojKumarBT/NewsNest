package com.cts.newsnest.authservice.service;

import com.cts.newsnest.authservice.config.JWTTokenGenerator;
import com.cts.newsnest.authservice.config.KafkaConfig;
import com.cts.newsnest.authservice.domain.CompleteUser;
import com.cts.newsnest.authservice.domain.FullUser;
import com.cts.newsnest.authservice.domain.User;
import com.cts.newsnest.authservice.exception.UserAlreadyExistException;
import com.cts.newsnest.authservice.exception.UserNotFoundException;
import com.cts.newsnest.authservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


import static com.cts.newsnest.authservice.config.AppConstants.TOPIC_NAME_FOR_USER_PROFILE;
import static com.cts.newsnest.authservice.config.AppConstants.TOPIC_NAME_FOR_WISHLIST;

/*
 * This class is implementing the UserService interface. This class has to be annotated with
 * @Service annotation.
 * @Service indicates annotated class is a service
 * which hold business logic in the Service layer
 *
 * */

@Service
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    private JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private KafkaConfig kConfig;

    @Autowired
    private Gson gson;

    /**
     * To get the property values
     */
    @Value("${app.service.message1}")
    private String message1;

    @Value("${app.service.message2}")
    private String message2;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(CompleteUser completeUser) throws UserAlreadyExistException{
        User user = new User(completeUser.getEmail(), completeUser.getPassword());
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            log.warn("UserAlreadyExistException");
            throw new UserAlreadyExistException("You are already registered! Please Login");
        }
        else{
            userRepository.save(user);
            kafkaTemplate.send(TOPIC_NAME_FOR_USER_PROFILE, gson.toJson(completeUser));
            kafkaTemplate.send(TOPIC_NAME_FOR_WISHLIST, gson.toJson(completeUser));
            log.info("User Registered Successfully");
            return "User Registered Successfully";
        }
    }


//    @Override
//    public User findByIdAndPassword(String id, String password) throws UserNotFoundException {
//        User authUser = userRepository.findByIdAndPassword(id, password);
//        if (authUser == null) {
//            throw new UserNotFoundException(message2);
//        }
//        return authUser;
//    }

    @Override
    public Map<String, String> loginUser(User user) throws UserNotFoundException{
        Optional<User> userInDB = userRepository.findByEmail(user.getEmail());
        if(userInDB.isPresent()){
            if(userInDB.get().getPassword().equals(user.getPassword())){
                log.info("Login success");
                return jwtTokenGenerator.generateToken(user);
            }
            else{
                log.warn("Invalid Credentials");
                throw new UserNotFoundException("Invalid Credentials");
            }
        }
        else{
            log.warn("You are not registered! Please register");
            throw new UserNotFoundException("You are not registered! Please register");
        }
    }

    @Override
    public void updateUser(String value) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        CompleteUser completeUser = objectMapper.readValue(value, CompleteUser.class);
        Optional<User> user = userRepository.findByEmail(completeUser.getEmail());

        User user1 = null;
        if(user.isPresent()){
            user1 = new User(completeUser.getEmail(), completeUser.getPassword());
        }

        log.info("Updating Auth details: " + value);
        userRepository.save(user1);
    }

    @Override
    @Transactional
    public void deleteUser(String value) throws JsonProcessingException{

        ObjectMapper objectMapper = new ObjectMapper();
        CompleteUser completeUser = objectMapper.readValue(value, CompleteUser.class);

        Optional<User> existingUser = userRepository.findByEmail(completeUser.getEmail());


        log.info("Deleting Auth details: " + value);
        userRepository.deleteByEmail(completeUser.getEmail());
    }
}
