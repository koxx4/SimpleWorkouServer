package com.koxx4.simpleworkout.simpleworkoutserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication(scanBasePackages = {
        "com.koxx4.simpleworkout.simpleworkoutserver.configuration"})
public class SimpleWorkoutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleWorkoutServerApplication.class, args);
        System.out.println("SimpleWorkout server started");
    }

}
