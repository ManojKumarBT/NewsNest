package com.cts.newsnest.user.config;

import com.cts.newsnest.user.repository.UserRepository;
import com.cts.newsnest.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.cts.newsnest.user.entity.User;

import java.util.Optional;

import static com.cts.newsnest.user.config.AppConstants.*;

/*
Add configuration annotation
 */
@Configuration
public class kafkaConfig {

    Logger log = LoggerFactory.getLogger(kafkaConfig.class);

//    @Autowired
//    UserService userService;

    @Autowired
    UserRepository userRepository;

//    public kafkaConfig(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }

    /*
    Build a new topic LOCATION_TOPIC_NAME
     */
//    String TOPIC_NAME_FOR_AUTH = AppConstants.TOPIC_NAME_FOR_AUTH;
//    String TOPIC_NAME_FOR_AUTH_UPDATE = AppConstants.TOPIC_NAME_FOR_AUTH_UPDATE;
//    String TOPIC_NAME_FOR_WISHLIST = AppConstants.TOPIC_NAME_FOR_WISHLIST;
//
//
    Optional<Integer> optionalInteger = Optional.of(1);
    Optional<Short> optionalShort = Optional.of((short)1);
//
//    @Bean
//    public NewTopic topicAuth(){
//        return new NewTopic(TOPIC_NAME_FOR_AUTH, optionalInteger, optionalShort);
//    }
//
    @Bean
    public NewTopic topicAuthUpdate(){
        return new NewTopic(TOPIC_NAME_FOR_AUTH_UPDATE, optionalInteger, optionalShort);
    }

    @Bean
    public NewTopic topicAuthDelete(){
        return new NewTopic(TOPIC_NAME_FOR_AUTH_DELETE, optionalInteger, optionalShort);
    }

    @Bean
    public NewTopic topicWishlistDelete(){
        return new NewTopic(TOPIC_NAME_FOR_WISHLIST_DELETE, optionalInteger, optionalShort);
    }
//
//    @Bean
//    public NewTopic topicWishList(){
//        return new NewTopic(TOPIC_NAME_FOR_WISHLIST, optionalInteger, optionalShort);
//    }

    @KafkaListener(topics = TOPIC_NAME_FOR_USER_PROFILE, groupId = GROUP_ID)
    public void registerUser(String value) throws JsonProcessingException {
//        userService.registerUser(value);

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(value, User.class);
        log.info("Registered New User");
        userRepository.save(user);
    }
}
