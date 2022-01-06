package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "/data/users", collectionResourceRel = "/data/users")
@CrossOrigin
public interface JpaUserRestRepository extends PagingAndSortingRepository<JpaUser, Long> {
    JpaUser findByNickname(@Param("nickname") String nickname);
    JpaUser findByEmail(@Param("email") String email);
}
