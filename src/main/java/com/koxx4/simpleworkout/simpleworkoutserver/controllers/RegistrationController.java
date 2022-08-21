package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.configuration.SpringFoxConfig;
import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.SQLException;

@RestController
@RequestMapping("register")
@CrossOrigin
@Validated
@Api(tags = {SpringFoxConfig.REGISTRATION_CONTROLLER})
class RegistrationController {

    private final UserService userService;

    @Autowired
    RegistrationController(UserService userService) {

        this.userService = userService;
    }


    @PostMapping("/user")
    public ResponseEntity<AppUser> registerNewUser(@NotBlank @RequestParam String username,
                                                    @Email @RequestParam String email,
                                                    @NotBlank @RequestParam CharSequence password) throws SQLException {

        AppUser createdUser  = userService.saveUser(username, email, password, new String[]{"REGULAR_USER"});

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/verify/{nickname}")
    public boolean verifyThatUserExists(@NotBlank @PathVariable String nickname) {

        return userService.existsByNickname(nickname);
    }
}