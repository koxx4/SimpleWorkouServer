package com.koxx4.simpleworkout.simpleworkoutserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Logger;

@SpringBootApplication(scanBasePackages = {
        "com.koxx4.simpleworkout.simpleworkoutserver.**"})
@EnableJpaRepositories(basePackages = {"com.koxx4.simpleworkout.simpleworkoutserver.repositories"})
public class SimpleWorkoutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleWorkoutServerApplication.class, args);
        System.out.println("SimpleWorkout server started");
    }

}
