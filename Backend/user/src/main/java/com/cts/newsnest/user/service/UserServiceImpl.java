package com.cts.newsnest.user.service;

import com.cts.newsnest.user.config.kafkaConfig;
import com.cts.newsnest.user.config.AppConstants;
import com.cts.newsnest.user.entity.User;
import com.cts.newsnest.user.exception.UserAlreadyExistsException;
import com.cts.newsnest.user.exception.UserNotFoundException;
import com.cts.newsnest.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private kafkaConfig kConfig;

    public UserServiceImpl(kafkaConfig kConfig) {
        this.kConfig = kConfig;
    }

    @Autowired
    private Gson gson;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

//    public User registerUser(User user){
//        Optional<User> idOptional = userRepository.findById(user.getId());
//        Optional<User> emailOptional = userRepository.findUserByEmail(user.getEmail());
//        if(idOptional.isPresent()){
//            throw new UserAlreadyExistsException("User with ID " + user.getId() + " is already exists.");
//        } else if (emailOptional.isPresent()) {
//            throw new UserAlreadyExistsException("User with EmailId " + user.getEmail() + " is already exists.");
//        } else{
//            User user1 = userRepository.save(user);
////            kafkaTemplate.send(AppConstants.TOPIC_NAME_FOR_AUTH, gson.toJson(user1));
////            kafkaTemplate.send(AppConstants.TOPIC_NAME_FOR_WISHLIST, gson.toJson(user1));
//            return userRepository.save(user);
//        }
//    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            log.info("Retrieved User By Id");
            return optional.get();
        }
        else{
            throw new UserNotFoundException("User not found with the ID: " + id);
        }
    }

    public User getUserByEmail(String email){
        Optional<User> optional = userRepository.findUserByEmail(email);
        if(optional.isPresent()){
            log.info("Retrieved User By EmailId");
            return optional.get();
        }
        else{
            throw new UserNotFoundException("User not found with the Email: " + email);
        }
    }

    public String updateUser(User user){
        Boolean passwordChanged = false;
        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
        if(existingUser.isPresent()){
            System.out.println(existingUser.get().getPassword());
            if(!user.getPassword().equals(existingUser.get().getPassword())){
//                System.out.println("IN IF");
                passwordChanged = true;
            }
            User updateUser = existingUser.get();
            System.out.println(updateUser.getPassword());
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());;
            updateUser.setPhoneNumber(user.getPhoneNumber());
            updateUser.setPassword(user.getPassword());
            User updatedUser = userRepository.save(updateUser);
            System.out.println(user.getPassword());
            System.out.println(existingUser.get().getPassword());
            if(passwordChanged){
                kafkaTemplate.send(AppConstants.TOPIC_NAME_FOR_AUTH_UPDATE,gson.toJson(updatedUser));
            }
            log.info("User Details Updated");
            return "User Details Updated Successfully";
        }
        else{
            throw new UserNotFoundException("User not found with the EmailId: " + user.getEmail());
        }
    }

    public String deleteUserById(int id){
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            userRepository.deleteById(id);
            kafkaTemplate.send(AppConstants.TOPIC_NAME_FOR_AUTH_DELETE,gson.toJson(optional.get()));
            kafkaTemplate.send(AppConstants.TOPIC_NAME_FOR_WISHLIST_DELETE,gson.toJson(optional.get()));
            log.info("Deleted the user with Id: " + id);
            return "Deleted the user with Id: " + id;
        }
        else{
            throw new UserNotFoundException("User not found with the ID: " + id);
        }
    }

    @Transactional
    public String deleteUserByEmailId(String email){
        Optional<User> optional = userRepository.findUserByEmail(email);
        if(optional.isPresent()){
            userRepository.deleteByEmail(email);
            kafkaTemplate.send(AppConstants.TOPIC_NAME_FOR_AUTH_DELETE,gson.toJson(optional.get()));
            kafkaTemplate.send(AppConstants.TOPIC_NAME_FOR_WISHLIST_DELETE,gson.toJson(optional.get()));
            log.info("Deleted the user with EmailId: " + email);
            return "Deleted the user with EmailId: " + email;
        }
        else{
            throw new UserNotFoundException("User not found with the EmailID: " + email);
        }
    }


    public String deleteAllUsers(){
        userRepository.deleteAll();
        return "Deleted All Users";
    }


    public void registerUser(String value) throws JsonProcessingException {


    }

}
