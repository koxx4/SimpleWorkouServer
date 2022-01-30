package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserWorkoutRepository extends CrudRepository<UserWorkout, Long> {
    Optional<UserWorkout> findByIdAndAppUserNickname(long id, String nickname);
    void deleteById(long id);
    void deleteByAppUserNicknameAndId(String appUser_nickname, long id);
}
