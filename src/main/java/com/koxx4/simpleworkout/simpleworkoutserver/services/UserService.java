package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchAppUserException;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchWorkoutException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService{
    AppUser saveUser(AppUser user, CharSequence password) throws SQLException;
    AppUser saveUser(String nickname, String email, CharSequence password, String[] roles) throws SQLException;
    UserWorkout addWorkoutEntryToUser(String nickname, UserWorkout workout) throws NoSuchAppUserException;
    void changeUserNickname(String oldNickname, String newNickname) throws NoSuchAppUserException;
    void changeUserEmail(String userNickname, String newEmail) throws NoSuchAppUserException;
    void changeUserPassword(String userNickname, CharSequence newPassword) throws NoSuchAppUserException;
    void deleteUserWorkoutEntry(String userNickname, long workoutId) throws NoSuchAppUserException, NoSuchWorkoutException;
    void deleteUserWorkoutEntry(String userNickname, UserWorkout userWorkout) throws NoSuchAppUserException, NoSuchWorkoutException;
    void deleteAllUserWorkoutEntries(String userNickname) throws NoSuchAppUserException;
    void deleteUser(String nickname) throws NoSuchAppUserException;
    void deleteUser(long id) throws NoSuchAppUserException;
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Optional<List<UserWorkout>> getAllUserWorkouts(String nickname);
    Optional<UserWorkout> getUserWorkout(String nickname, long workoutId);
    Optional<AppUser> getUserById(long id);
    Optional<AppUser> getUserByNickname(String nickname);
}
