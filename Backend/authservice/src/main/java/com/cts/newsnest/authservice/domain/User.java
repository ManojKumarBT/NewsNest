package com.cts.newsnest.authservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "UserDetails")
public class User {

    @Id
    @Column(name = "email", length = 50)
    @NotBlank(message = "Email cannot be empty")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,73}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email is Invalid")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 24, message = "Password is wrong")
    private String password;

    public User() {
        super();
    }

    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setId(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
