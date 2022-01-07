package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserRole;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("register")
@CrossOrigin
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    private ResponseEntity<AppUser> registerNewUser(@RequestParam(name = "username") String username,
                                                    @RequestParam(name = "email") String email,
                                                    @RequestParam(name = "password") CharSequence password) throws SQLException {

        var createdUser  = userService.saveUser(username, email, password, new String[]{"REGULAR_USER"});
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/verify/{nickname}")
    private boolean verifyThatUserExists(@PathVariable String nickname){
        return userService.existsByNickname(nickname);
    }

}
