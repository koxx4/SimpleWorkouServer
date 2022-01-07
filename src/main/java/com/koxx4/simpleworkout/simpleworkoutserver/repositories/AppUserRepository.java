package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findByNickname(String nickname);
    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    void deleteByNickname(String nickname);
}
