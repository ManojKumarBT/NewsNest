package com.cts.newsnest.authservice.repository;

import com.cts.newsnest.authservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * This class is implementing the JpaRepository interface for User.
 * @Repository marks the specific class as a Data Access Object
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public Optional<User> findByEmail(String email);

    public User findByIdAndPassword(String id, String password);

    public void deleteByEmail(String email);

}
