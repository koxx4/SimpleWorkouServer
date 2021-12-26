package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import org.springframework.data.repository.CrudRepository;

public interface UserWorkoutRepository extends CrudRepository<UserWorkout, Long> {
}
