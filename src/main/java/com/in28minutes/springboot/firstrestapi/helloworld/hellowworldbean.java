package com.in28minutes.springboot.firstrestapi.helloworld;

import org.springframework.context.annotation.Bean;

public class hellowworldbean {

    private String message;

    public hellowworldbean(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "hellowworldbean{" +
                "message='" + message + '\'' +
                '}';
    }
}
