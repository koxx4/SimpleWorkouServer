package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "workout", collectionResourceRel = "workouts")
@CrossOrigin
public interface AppUserWorkoutRestRepository extends PagingAndSortingRepository<UserWorkout, Long> {
    List<UserWorkout> getAllByAppUserNickname(@Param("nickname") String nickname);
}
