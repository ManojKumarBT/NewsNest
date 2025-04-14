package com.cts.newsnest.newsservice.service;

import com.cts.newsnest.newsservice.entity.Article;
import com.cts.newsnest.newsservice.entity.MainNews;
import com.cts.newsnest.newsservice.exception.InvalidCountryCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.logging.Logger;

@Service
public class NewsServiceImpl implements NewsService {

    Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);
//    log.info("Logging");

    private static final String API_KEY = "dd510ff6ec254e1b83cc00e162611b02";

    private static final String API_URL = "https://newsapi.org/v2/top-headlines";

    private RestTemplate restTemplate = new RestTemplate();

//    public NewsServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    String[] countriesArray = {"ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","us","ve","za"};

    List<String> listOfCountries = new ArrayList<>(Arrays.asList(countriesArray));


    public MainNews getNews(String country) throws Exception{
        MainNews news;
        List<Article> articles;

        for(String element : listOfCountries){
            if(element.equals(country)){
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URL)
                        .queryParam("apiKey", API_KEY)
                        .queryParam("country", country);
                String urlWithParams = builder.toUriString();
                ResponseEntity<MainNews> response = restTemplate.exchange(urlWithParams, HttpMethod.GET, null, MainNews.class);

                news = response.getBody();
                if(news.getArticles() == null){
                    log.warn("No Articles Found- Null Point Exception");
                    throw new NullPointerException("No Articles Found");
                }

                log.info("Returning Articles!");
                return news;
            }
        }

        log.warn("Invalid CountryCode input- Exception");
        throw new InvalidCountryCodeException("You entered a invalid country code");
    }
}
