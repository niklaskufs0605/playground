package com.niklas.playground.base52;

import com.niklas.playground.base52.code.AliasName;
import com.niklas.playground.base52.code.Base52;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Base52Application {
    public static void main(String[] args) {
        SpringApplication.run(Base52Application.class, args);

        System.out.println(AliasName.from(17));
        System.out.println(AliasName.getNextName("abcd"));
    }
}
