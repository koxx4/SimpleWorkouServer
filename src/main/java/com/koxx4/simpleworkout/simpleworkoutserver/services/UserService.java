package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;

import java.util.List;

public interface UserService{
    void addWorkoutEntryToUser(String nickname);
    void changeUserNickname(String oldNickname, String newNickname);
    void changeUserEmail(String userNickname, String newEmail);
    void deleteUserWorkoutEntry(String userNickname, long workoutId);
    void deleteAllUserWorkoutEntries(String userNickname);
    List<UserWorkout> getAllUserWorkouts(String nickname);
    UserWorkout getUserWorkout(long workoutId);
}
