package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserJPAService implements UserService{
    private final JpaUserRepository userRepository;
    private final JpaPasswordRepository passwordRepository;

    public UserJPAService(@Autowired JpaUserRepository jpaUserRepository,
                          @Autowired JpaPasswordRepository jpaPasswordRepository) {
        this.userRepository = jpaUserRepository;
        this.passwordRepository = jpaPasswordRepository;
    }

    @Override
    public void addWorkoutEntryToUser(String nickname) {

    }

    @Override
    public void changeUserNickname(String oldNickname, String newNickname) {
        Optional<JpaUser> fetchedUser = userRepository.findByNickname(oldNickname);

        fetchedUser.ifPresent( user -> {
            user.setNickname(newNickname);
            userRepository.save(user);
        });
    }

    @Override
    public void changeUserEmail(String userNickname, String newEmail) {

    }

    @Override
    public void deleteUserWorkoutEntry(String userNickname, long workoutId) {

    }

    @Override
    public void deleteAllUserWorkoutEntries(String userNickname) {

    }

    @Override
    public List<UserWorkout> getAllUserWorkouts(String nickname) {
        return null;
    }

    @Override
    public UserWorkout getUserWorkout(long workoutId) {
        return null;
    }
}
