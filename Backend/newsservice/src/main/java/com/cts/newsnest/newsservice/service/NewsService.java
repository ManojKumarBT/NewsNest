package com.cts.newsnest.newsservice.service;

import com.cts.newsnest.newsservice.entity.MainNews;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface NewsService {

    public MainNews getNews(String country) throws Exception;
}
