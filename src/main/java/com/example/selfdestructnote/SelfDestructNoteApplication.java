package com.example.selfdestructnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SelfDestructNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfDestructNoteApplication.class, args);
    }

}
