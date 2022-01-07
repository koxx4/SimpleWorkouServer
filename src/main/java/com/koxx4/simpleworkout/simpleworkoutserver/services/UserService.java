package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService{
    AppUser saveUser(AppUser user, CharSequence password) throws SQLException;
    AppUser saveUser(String nickname, String email, CharSequence password, String[] roles) throws SQLException;
    void addWorkoutEntryToUser(String nickname, UserWorkout workout);
    void changeUserNickname(String oldNickname, String newNickname);
    void changeUserEmail(String userNickname, String newEmail);
    void changeUserPassword(String userNickname, CharSequence newPassword);
    void deleteUserWorkoutEntry(String userNickname, long workoutId);
    void deleteAllUserWorkoutEntries(String userNickname);
    void deleteUser(String nickname);
    void deleteUser(long id);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Optional<List<UserWorkout>> getAllUserWorkouts(String nickname);
    Optional<UserWorkout> getUserWorkout(String nickname, long workoutId);
}
