package com.cts.newsnest.wishlist.controller;

import com.cts.newsnest.wishlist.entity.Article;
import com.cts.newsnest.wishlist.entity.FullUser;
import com.cts.newsnest.wishlist.entity.WishList;
import com.cts.newsnest.wishlist.exception.ArticleAlreadyExistsException;
import com.cts.newsnest.wishlist.exception.ArticleNotFound;
import com.cts.newsnest.wishlist.exception.EmptyWishListException;
import com.cts.newsnest.wishlist.exception.UserNotFoundException;
import com.cts.newsnest.wishlist.repository.WishListRepository;
import com.cts.newsnest.wishlist.service.WishListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping("/newsnest/v1")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex){
        return new ResponseEntity<>("An Error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleAlreadyExistsException.class)
    public ResponseEntity<?> handleArticleAlreadyExistsException(ArticleAlreadyExistsException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyWishListException.class)
    public ResponseEntity<?> handleEmptyWishListException(EmptyWishListException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(ArticleNotFound.class)
    public ResponseEntity<?> handleArticleNotFound(ArticleNotFound ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException exception){
        Map <String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return  errors;
    }


    @PostMapping("/article/save")
    public ResponseEntity<?> saveArticle(@RequestParam String email,@Valid @RequestBody Article article) throws Exception{

        try {
            return new ResponseEntity<>(wishListService.saveArticle(email, article), HttpStatus.CREATED);
        }
        catch (UserNotFoundException | ArticleAlreadyExistsException ex){
            throw ex;
        }
        catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }

    @GetMapping("/articles")
    public ResponseEntity<?> getArticlesByUserId(@RequestParam String email) throws Exception{
        try{
            return new ResponseEntity<>(wishListService.getArticlesByUserId(email), HttpStatus.OK);
        }
        catch (UserNotFoundException | EmptyWishListException ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
        catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }

    @DeleteMapping("/delete/articles")
    public ResponseEntity<?> deleteAllArticlesByUserId(@RequestParam String email) throws Exception{
        try{
            return new ResponseEntity<>(wishListService.deleteAllArticlesByUserId(email),HttpStatus.OK);
        }
        catch (UserNotFoundException | EmptyWishListException ex){
            throw ex;
        }
        catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }

    @DeleteMapping("/delete/article/{articleId}")
    public ResponseEntity<?> deleteArticleByArticleId(@PathVariable int articleId) throws Exception{
        try{
            return new ResponseEntity<>(wishListService.deleteArticleByArticleId(articleId), HttpStatus.OK);
        }
        catch (ArticleNotFound ex){
            throw ex;
        }
        catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }
}
