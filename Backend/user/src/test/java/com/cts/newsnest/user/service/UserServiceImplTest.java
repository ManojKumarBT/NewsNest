package com.cts.newsnest.user.service;


import com.cts.newsnest.user.entity.User;
import com.cts.newsnest.user.exception.UserNotFoundException;
import com.cts.newsnest.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void givenExistingUserId_whenGetUserById_thenReturnUser() {
        User user = new User(1, "Sachin", "Tendulkar", "Sachin@gmail.com", "9999999999", "Sachin@123");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Getting user by ID
        User foundUser = userService.getUserById(1);

        // Assertions
        assertEquals(1, foundUser.getId());
        assertEquals("Sachin", foundUser.getFirstName());
        assertEquals("Tendulkar", foundUser.getLastName());
        assertEquals("Sachin@gmail.com", foundUser.getEmail());
        assertEquals("9999999999", foundUser.getPhoneNumber());
        assertEquals("Sachin@123", foundUser.getPassword());
    }

    @Test
    public void givenNonExistingUserId_whenGetUserById_thenThrowUserNotFoundException() {
        // Mocking userRepository behavior for non-existing user
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        // Assertion for UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(2));
    }
}

