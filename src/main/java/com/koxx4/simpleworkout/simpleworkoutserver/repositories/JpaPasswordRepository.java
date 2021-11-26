package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUserPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaPasswordRepository extends CrudRepository<JpaUserPassword, Long> {
    JpaUserPassword findByJpaUserNickname(String nickname);
    JpaUserPassword findByJpaUserEmail(String email);
}
