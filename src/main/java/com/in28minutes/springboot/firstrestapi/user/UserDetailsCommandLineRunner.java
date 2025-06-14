package com.in28minutes.springboot.firstrestapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

    private UserDetailsRepository repository;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetails("Ona", "Admin"));
        repository.save(new UserDetails("Sihle", "User"));
        repository.save(new UserDetails("Zwane", "Admin"));

        List<UserDetails> users = repository.findAll();
        repository.findByRole("Admin");
        users.forEach(user -> logger.info("UserDetails: {}", user));



    }
}
