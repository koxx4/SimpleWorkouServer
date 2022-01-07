package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource(path = "user", collectionResourceRel = "users")
@CrossOrigin
public interface AppUserRestRepository extends PagingAndSortingRepository<AppUser, Long> {
    Optional<AppUser> findByNickname(@Param("nickname") String nickname);
    Optional<AppUser> findByEmail(@Param("email") String email);
}
