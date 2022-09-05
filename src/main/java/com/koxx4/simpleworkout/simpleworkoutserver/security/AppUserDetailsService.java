package com.koxx4.simpleworkout.simpleworkoutserver.security;

import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    @Autowired
    public AppUserDetailsService(AppUserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByNickname(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not found: " + username));
    }
}
