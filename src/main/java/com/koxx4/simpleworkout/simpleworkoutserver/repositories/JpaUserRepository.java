package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends CrudRepository<JpaUser, Long> {
    JpaUser findByNickname(String nickname);
    JpaUser findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    void deleteByNickname(String nickname);
}
