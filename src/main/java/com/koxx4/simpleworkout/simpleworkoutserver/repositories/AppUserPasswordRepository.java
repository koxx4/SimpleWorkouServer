package com.koxx4.simpleworkout.simpleworkoutserver.repositories;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUserPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppUserPasswordRepository extends CrudRepository<AppUserPassword, Long> {
    AppUserPassword findByAppUserNickname(String nickname);
    AppUserPassword findByAppUserEmail(String email);
}
