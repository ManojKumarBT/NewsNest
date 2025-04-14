package com.cts.newsnest.user.controller;

import com.cts.newsnest.user.entity.User;
import com.cts.newsnest.user.exception.UserAlreadyExistsException;
import com.cts.newsnest.user.exception.UserNotFoundException;
import com.cts.newsnest.user.exception.InvalidInputException;
import com.cts.newsnest.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("newsnest/v1")
public class UserController {

    @Autowired
    UserService userService;


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(InvalidInputException.class)
//    public Map<?,?> handleInvalidInputException(InvalidInputException ex) {
////        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
//        Map<> map = new HashMap<>();
//    }

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


//    @PostMapping("/register")
//    public ResponseEntity<?> RegisterUser(@Valid @RequestBody User user){
//        User savedUser;
//        try{
//            savedUser = userService.registerUser(user);
//            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//        }
//        catch (UserAlreadyExistsException ex){
//            throw ex;
//        }
//        catch (Exception ex){
//            throw new RuntimeException(ex);
//        }
//
//    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        try{
            List<User> allUsers = userService.getAllUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        try{
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserByEmail(@Param("email") String email){
        try{
            User user = userService.getUserByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    @PutMapping("/update/user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user){
        try{
            return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
        }
        catch (UserNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id){
        try{
            return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
        }
        catch (UserNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @DeleteMapping("delete/user")
    public ResponseEntity<?> deleteUserByEmail(@Param("email") String email){
        return new ResponseEntity<>(userService.deleteUserByEmailId(email), HttpStatus.OK);
    }

    @DeleteMapping("/delete/allusers")
    public ResponseEntity<?> deleteAllUsers(){
        return ResponseEntity.ok(userService.deleteAllUsers());
    }


}
