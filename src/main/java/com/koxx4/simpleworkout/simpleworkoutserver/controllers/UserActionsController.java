package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchAppUserException;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchWorkoutException;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("user/actions/{nickname}")
@CrossOrigin
@Validated
public class UserActionsController {
    private final UserService userService;

    public UserActionsController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("nickname")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void changeUserNickname(@PathVariable String nickname,
                                   @NotBlank @RequestParam String newNickname) throws NoSuchAppUserException {
        userService.changeUserNickname(nickname, newNickname);
    }

    @PostMapping("password")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void changeUserPassword(@PathVariable String nickname,
                                   @NotBlank @RequestParam CharSequence password) throws NoSuchAppUserException {
        userService.changeUserPassword(nickname, password);
    }

    @GetMapping("workout")
    public ResponseEntity<List<UserWorkout>> getAllUserWorkouts(@PathVariable String nickname) throws NoSuchWorkoutException {
        var workouts = userService.getAllUserWorkouts(nickname);
        return new ResponseEntity<>(workouts.orElseThrow(NoSuchWorkoutException::new), HttpStatus.OK);
    }

    @PostMapping("workout")
    public ResponseEntity<UserWorkout> addUserWorkout(@PathVariable String nickname,
                               @RequestBody(required = true) UserWorkout workout) throws NoSuchAppUserException {
        var savedWorkout = userService.addWorkoutEntryToUser(nickname, workout);
        return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);
    }

    @DeleteMapping("workout")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteUserWorkout(@PathVariable String nickname,
                               @RequestParam(required = true) Long id) throws NoSuchAppUserException, NoSuchWorkoutException {
        userService.deleteUserWorkoutEntry(nickname, id);
    }

    @GetMapping("data")
    public ResponseEntity<AppUser> getUserData(@NotBlank @PathVariable String nickname) throws NoSuchAppUserException {
        var foundUser = userService.getUserByNickname(nickname);
        return new ResponseEntity<>(foundUser.orElseThrow(NoSuchAppUserException::new), HttpStatus.OK);
    }

    @DeleteMapping("data")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteUserData(@NotBlank @PathVariable String nickname) throws NoSuchAppUserException {
        userService.deleteUser(nickname);
    }


}
