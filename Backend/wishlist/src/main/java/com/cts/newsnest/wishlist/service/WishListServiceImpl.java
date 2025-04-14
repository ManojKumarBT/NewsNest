package com.cts.newsnest.wishlist.service;

import com.cts.newsnest.wishlist.entity.Article;
import com.cts.newsnest.wishlist.entity.FullUser;
import com.cts.newsnest.wishlist.entity.WishList;
import com.cts.newsnest.wishlist.exception.ArticleAlreadyExistsException;
import com.cts.newsnest.wishlist.exception.ArticleNotFound;
import com.cts.newsnest.wishlist.exception.EmptyWishListException;
import com.cts.newsnest.wishlist.exception.UserNotFoundException;
import com.cts.newsnest.wishlist.repository.ArticleRepository;
import com.cts.newsnest.wishlist.repository.JoiningEntityRepository;
import com.cts.newsnest.wishlist.repository.WishListRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImpl implements  WishListService {

    Logger log = LoggerFactory.getLogger(WishListServiceImpl.class);

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    JoiningEntityRepository joiningEntityRepository;

    public String saveArticle(String email, Article article) throws Exception{
        Optional<WishList> existingUser = wishListRepository.findByEmail(email);

        if(existingUser.isPresent()){
            Optional<Article> existingArticle = articleRepository.findByUrlAndUserId(article.getUrl(), existingUser.get().getUserId());
            if(existingArticle.isPresent()){
                throw new ArticleAlreadyExistsException("Article already saved into Wishlist");
            }
            article.setUser(existingUser.get());
            articleRepository.save(article);
            log.info("Article saved successfully!");
            return "Article saved successfully!";
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    public List<Article> getArticlesByUserId(String email) throws Exception{
        Optional<WishList> existingUser = wishListRepository.findByEmail(email);

        if(existingUser.isPresent()){
            List<Article> articles =  articleRepository.findByUserId(existingUser.get().getUserId());
                if(articles.isEmpty()){
                    System.out.println("Empty");
                    throw new EmptyWishListException("WishList is Empty");
                }
                log.info("Retrieve Articles");
                return articles;
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    public String deleteAllArticlesByUserId(String email) throws Exception{
        Optional<WishList> existingUser = wishListRepository.findByEmail(email);

        if(existingUser.isPresent()){
            List<Article> articles =  articleRepository.findByUserId(existingUser.get().getUserId());
            if(!articles.isEmpty()) {
                articleRepository.deleteAllByUserId(existingUser.get().getUserId());
                log.info("Wishlist Cleared!");
                return "Wishlist Cleared!";
            }
            else {
                throw new EmptyWishListException("Wishlist is Empty");
            }
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    public String deleteArticleByArticleId(int articleId) throws Exception{
        Optional<Article> existingArticle = articleRepository.findByArticleId(articleId);
        if(existingArticle.isPresent()){
            articleRepository.deleteArticleByArticleId(articleId);
            log.info("Article deleted!");
            return "Article deleted!";
        }
        else{
            throw new ArticleNotFound("Article with articleId " + articleId + " not found");
        }
    }


    public void addUser(String value) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        FullUser fullUser = objectMapper.readValue(value, FullUser.class);

        WishList user = new WishList();
        user.setEmail(fullUser.getEmail());

        log.info("User Added");
        wishListRepository.save(user);
    }

    @Transactional
    public void deleteUser(String value) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        FullUser fullUser = objectMapper.readValue(value, FullUser.class);

        String email = fullUser.getEmail();

        Optional<WishList> existingUser = wishListRepository.findByEmail(email);
        if(existingUser.isPresent()){
            log.info("All Articles related to the " + email + " are deleted");
            articleRepository.deleteAllByUserId(existingUser.get().getUserId());

            log.info("User Deleted");
            wishListRepository.deleteByEmail(email);
        }
    }
}





