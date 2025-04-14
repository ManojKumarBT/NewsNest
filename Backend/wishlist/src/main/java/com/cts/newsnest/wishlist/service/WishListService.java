package com.cts.newsnest.wishlist.service;

import com.cts.newsnest.wishlist.entity.Article;
import com.cts.newsnest.wishlist.entity.WishList;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface WishListService {

    public String saveArticle(String email, Article article) throws Exception;

    public List<Article> getArticlesByUserId(String email) throws Exception;

    public String deleteAllArticlesByUserId(String email) throws Exception;

    public String deleteArticleByArticleId(int articleId) throws Exception;

    public void addUser(String value) throws JsonProcessingException;

    public void deleteUser(String value) throws JsonProcessingException;
}
