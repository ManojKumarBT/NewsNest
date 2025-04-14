package com.cts.newsnest.authservice.config;

import com.cts.newsnest.authservice.domain.CompleteUser;
import com.cts.newsnest.authservice.domain.FullUser;
import com.cts.newsnest.authservice.domain.User;
import com.cts.newsnest.authservice.repository.UserRepository;
import com.cts.newsnest.authservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import com.cts.newsnest.authservice.config.AppConstants;

import java.util.Optional;

//import static com.cts.newsnest.authservice.config.AppConstants.GROUP_ID;
//import static com.cts.newsnest.authservice.config.AppConstants.TOPIC_NAME_FOR_AUTH;
//import static com.cts.newsnest.authservice.config.AppConstants.TOPIC_NAME_FOR_AUTH_UPDATE;
import static com.cts.newsnest.authservice.config.AppConstants.*;


@Configuration
public class KafkaConfig {

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    TriggerClass triggerClass;

//    String LOCATION_UPDATE_TOPIC = AppConstants.LOCATION_UPDATE_TOPIC;
//    String GROUP_ID = AppConstants.GROUP_ID;

//    public static final String LOCATION_UPDATE_TOPIC="location-update-topic";
//    public static final String GROUP_ID="producer-1";
    /*
    Provide KafkaListener config annotation
     */
//    @KafkaListener(topics = TOPIC_NAME_FOR_AUTH, groupId = GROUP_ID)
//    public void registerUser(String value) throws JsonProcessingException {
//        System.out.println(value);
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        FullUser fullUser = objectMapper.readValue(value, FullUser.class);
//
//        User user = new User(fullUser.getEmail(), fullUser.getPassword());
//        userRepository.save(user);
//
////        System.out.println(this.getBrokerMessage(value));
//    }
//
    @Transactional
    @KafkaListener(topics = TOPIC_NAME_FOR_AUTH_UPDATE, groupId = GROUP_ID)
    public void updateUser(String value) throws JsonProcessingException{
//        userService.updateUser(value);
        ObjectMapper objectMapper = new ObjectMapper();
        FullUser fullUser = objectMapper.readValue(value, FullUser.class);
        Optional<User> user = userRepository.findByEmail(fullUser.getEmail());

        User user1 = null;
        if(user.isPresent()){
            user1 = new User(fullUser.getEmail(), fullUser.getPassword());
        }

//        log.info("Updating Auth details: " + value);
        userRepository.save(user1);
    }

    @Transactional
    @KafkaListener(topics = TOPIC_NAME_FOR_AUTH_DELETE, groupId = GROUP_ID)
    public void deleteUser(String value) throws JsonProcessingException{
//        userService.deleteUser(value);
        ObjectMapper objectMapper = new ObjectMapper();
        FullUser fullUser = objectMapper.readValue(value, FullUser.class);

        Optional<User> existingUser = userRepository.findByEmail(fullUser.getEmail());


//        log.info("Deleting Auth details: " + value);
        userRepository.deleteByEmail(fullUser.getEmail());
    }



    Optional<Integer> noOfPartitions = Optional.of(1);
    Optional<Short> noOfReplicas = Optional.of((short)1);

    @Bean
    public NewTopic topicForUserProfile(){
        return new NewTopic(TOPIC_NAME_FOR_USER_PROFILE, noOfPartitions, noOfReplicas);
    }

    @Bean
    public NewTopic topicForWishlist(){
        return new NewTopic(TOPIC_NAME_FOR_WISHLIST, noOfPartitions, noOfReplicas);
    }

}
