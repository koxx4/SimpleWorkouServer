package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userPrivate/modifyUser")
@CrossOrigin
public class UserDataModificationController {
    private final UserService userService;

    public UserDataModificationController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void changeUserNickname(@RequestParam String currentNickname,
                                   @RequestParam String newNickname){
        userService.changeUserNickname(currentNickname, newNickname);
    }

}
