package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.User;

public interface UserService {
    User getUserByNickname(String username);
    User getUserByEmail(String email);

}
