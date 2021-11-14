package com.koxx4.simpleworkout.simpleworkoutserver.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProfileManager {
    @Value("${spring.profiles.active:}")
    private String activeProfiles;

    @PostConstruct
    public void printActiveProfiles() {
        for (String profileName : activeProfiles.split(",")) {
            System.out.println(">>>>> Currently active profile - " + profileName);
        }
    }

}