package com.koxx4.simpleworkout.simpleworkoutserver.security;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUserPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {

    private final JpaUser jpaUser;

    public SecurityUser(JpaUser user){
        this.jpaUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return jpaUser.getRoles();
    }

    @Override
    public String getPassword() {
        return jpaUser.getJpaPassword().getPassword();
    }

    @Override
    public String getUsername() {
        return jpaUser.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
