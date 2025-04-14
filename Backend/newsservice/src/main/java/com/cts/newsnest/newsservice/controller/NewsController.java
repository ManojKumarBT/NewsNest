package com.cts.newsnest.newsservice.controller;

import com.cts.newsnest.newsservice.entity.MainNews;
import com.cts.newsnest.newsservice.exception.InvalidCountryCodeException;
import com.cts.newsnest.newsservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("newsnest/v1")
public class NewsController {

    @Autowired
    NewsService newsService;

//    Logger log = Logger.getLogger(NewsController.class);


    @ExceptionHandler(InvalidCountryCodeException.class)
    public ResponseEntity<?> handleInvalidCountryCodeException(InvalidCountryCodeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/news/{country}")
    public ResponseEntity<?> getNews(@PathVariable String country) throws Exception{
        try{
            MainNews news = newsService.getNews(country);
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        catch (InvalidCountryCodeException | NullPointerException ex){
            throw ex;
        }
        catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }
}
