package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Temporal;
import java.util.List;

@RestController
@RequestMapping("user/actions/{nickname}")
@CrossOrigin
public class UserActionsController {
    private final UserService userService;

    public UserActionsController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("nickname")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void changeUserNickname(@PathVariable String nickname,
                                   @RequestParam(name = "new") String newNickname){
        userService.changeUserNickname(nickname, newNickname);
    }

    @PostMapping("password")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void changeUserPassword(@PathVariable String nickname,
                                   @RequestParam CharSequence password){
        userService.changeUserPassword(nickname, password);
    }

    @GetMapping("workout")
    public ResponseEntity<List<UserWorkout>> getAllUserWorkouts(@PathVariable String nickname){
        var workouts = userService.getAllUserWorkouts(nickname);
        return new ResponseEntity<>(workouts.get(), HttpStatus.OK);
    }

    @PostMapping("workout")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addUserWorkout(@PathVariable String nickname,
                               @RequestBody(required = true) UserWorkout workout){
        userService.addWorkoutEntryToUser(nickname, workout);
    }

    @GetMapping("data")
    public ResponseEntity<AppUser> getUserData(@PathVariable String nickname){
        var foundUser = userService.getUserByNickname(nickname);
        return new ResponseEntity<>(foundUser.get(), HttpStatus.OK);
    }


}
