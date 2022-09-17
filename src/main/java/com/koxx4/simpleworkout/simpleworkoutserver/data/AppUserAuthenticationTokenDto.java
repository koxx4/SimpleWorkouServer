package com.koxx4.simpleworkout.simpleworkoutserver.data;

import java.util.List;

public class AppUserAuthenticationTokenDto {

    private final String nickname;
    private final String password;
    private final List<UserRole> roles;

    public AppUserAuthenticationTokenDto(String nickname, String password, List<UserRole> roles) {

        this.nickname = nickname;
        this.password = password;
        this.roles = roles;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }
}
