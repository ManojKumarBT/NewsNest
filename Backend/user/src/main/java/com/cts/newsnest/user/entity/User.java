package com.cts.newsnest.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(
        name="user",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"email"})
)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
//    @Column(nullable = false)
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email is mandatory")
//    @Column(unique=true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,73}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email is Invalid")
    private String email;
//    @Column(nullable = false)

    @NotBlank(message = "Phone Number is mandatory")
//    @Range(min = 9, max = 11, message = "Phone Number must be of length 10")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone Number is Invalid")
//    @Column(nullable = false)
    private String phoneNumber;
//    @Column(nullable = false)
//    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 24, message = "Password Size should be in range of 8 to 24")
    private String password;

    public User(){
    }

    public User(int id, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(int id, String firstName, String email, String phoneNumber, String password) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
