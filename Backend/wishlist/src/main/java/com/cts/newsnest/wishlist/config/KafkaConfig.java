package com.cts.newsnest.wishlist.config;

import com.cts.newsnest.wishlist.entity.FullUser;
import com.cts.newsnest.wishlist.entity.WishList;
import com.cts.newsnest.wishlist.repository.ArticleRepository;
import com.cts.newsnest.wishlist.repository.WishListRepository;
//import com.cts.newsnest.wishlist.repository.WishListRepository;
import com.cts.newsnest.wishlist.service.WishListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

import static com.cts.newsnest.wishlist.config.AppConstants.*;

@Configuration
public class KafkaConfig {

    Logger log = LoggerFactory.getLogger(KafkaConfig.class);

//    @Autowired
//    WishListService wishListService;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ArticleRepository articleRepository;


    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void topicAddUser(String value) throws JsonProcessingException {

//        wishListService.addUser(value);

        ObjectMapper objectMapper = new ObjectMapper();
        FullUser fullUser = objectMapper.readValue(value, FullUser.class);

        WishList user = new WishList();
        user.setEmail(fullUser.getEmail());

        log.info("User Added");
        wishListRepository.save(user);
    }

    @Transactional
    @KafkaListener(topics = TOPIC_NAME_FOR_WISHLIST_DELETE, groupId = GROUP_ID)
    public void topicDeleteUser(String value) throws JsonProcessingException {

//        wishListService.deleteUser(value);
        ObjectMapper objectMapper = new ObjectMapper();
        FullUser fullUser = objectMapper.readValue(value, FullUser.class);

        String email = fullUser.getEmail();

        Optional<WishList> existingUser = wishListRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            log.info("All Articles related to the " + email + " are deleted");
            articleRepository.deleteAllByUserId(existingUser.get().getUserId());

            log.info("User Deleted");
            wishListRepository.deleteByEmail(email);
        }
    }
}
