package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserRole;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import javax.xml.bind.SchemaOutputResolver;

@RestController
@RequestMapping("register")
public class RegistrationController {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(@Autowired JpaUserRepository userRepository,
                                  @Autowired PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user")
    private ResponseEntity<JpaUser> registerNewUser(@RequestParam(name = "username") String username,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name = "password") CharSequence password){

        JpaUser newJpaUser = new JpaUser(email, username);
        JpaUserPassword userPassword = new JpaUserPassword(newJpaUser, passwordEncoder.encode(password) );
        newJpaUser.addRole(new UserRole("USER"));
        newJpaUser.setJpaPassword(userPassword);

        userRepository.save(newJpaUser);

        return new ResponseEntity<>(newJpaUser, HttpStatus.CREATED);
    }

}
