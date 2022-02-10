package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.configuration.SpringFoxConfig;
import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.InvalidOldPasswordProvidedException;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchAppUserException;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchWorkoutException;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin
@Validated
@Api(tags = {SpringFoxConfig.userActionsController})
public class UserActionsController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserActionsController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("nickname")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void changeUserNickname(Authentication authentication,
                                   @NotBlank @RequestParam String newNickname) throws NoSuchAppUserException {
        userService.changeUserNickname(authentication.getName(), newNickname);
    }

    @PostMapping("password")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void changeUserPassword(Authentication authentication,
                                   @NotBlank @RequestParam CharSequence oldPassword,
                                   @NotBlank @RequestParam CharSequence newPassword) throws NoSuchAppUserException, InvalidOldPasswordProvidedException {
        if (!passwordEncoder.matches(oldPassword, (String)authentication.getCredentials()))
            throw new InvalidOldPasswordProvidedException();

          userService.changeUserPassword(authentication.getName(), newPassword);
    }

    @GetMapping("workout")
    public ResponseEntity<List<UserWorkout>> getAllUserWorkouts(Authentication authentication) throws NoSuchWorkoutException {
        var workouts = userService.getAllUserWorkouts(authentication.getName());
        return new ResponseEntity<>(workouts.orElseThrow(NoSuchWorkoutException::new), HttpStatus.OK);
    }

    @PostMapping("workout")
    public ResponseEntity<UserWorkout> addUserWorkout(Authentication authentication,
                               @RequestBody UserWorkout workout) throws NoSuchAppUserException {
        var savedWorkout = userService.addWorkoutEntryToUser(authentication.getName(), workout);
        return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);
    }

    @DeleteMapping("workout")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteUserWorkout(Authentication authentication,
                               @RequestParam Long id) throws NoSuchAppUserException, NoSuchWorkoutException {
        userService.deleteUserWorkoutEntry(authentication.getName(), id);
    }

    @GetMapping("data")
    public ResponseEntity<AppUser> getUserData(Authentication authentication) throws NoSuchAppUserException {
        var foundUser = userService.getUserByNickname(authentication.getName());
        return new ResponseEntity<>(foundUser.orElseThrow(NoSuchAppUserException::new), HttpStatus.OK);
    }

    @DeleteMapping("data")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteUserData(Authentication authentication) throws NoSuchAppUserException {
        userService.deleteUser(authentication.getName());
    }


}
