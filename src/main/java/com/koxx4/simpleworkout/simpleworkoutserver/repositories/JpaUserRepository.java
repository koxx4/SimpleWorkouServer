package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends CrudRepository<JpaUser, Long> {
    Optional<JpaUser> findByNickname(String nickname);
    Optional<JpaUser> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    void deleteByNickname(String nickname);
}
