package com.in28minutes.springboot.firstrestapi.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserDetails {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String role;


    public UserDetails(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public UserDetails() {

    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
