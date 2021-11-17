package com.koxx4.simpleworkout.simpleworkoutserver.security;

import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.JpaUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class JpaUserDetailsManager implements UserDetailsManager {

    private final JpaUserRepository userRepository;
    private final JpaPasswordRepository passwordRepository;

    @Autowired
    public JpaUserDetailsManager(JpaUserRepository userRepository, JpaPasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        if(!userRepository.existsByNickname(userDetails.getUsername())) {
            JpaUser newJpaUser = new JpaUser(null, userDetails.getUsername());
            JpaUserPassword newJpaUserPassword = new JpaUserPassword(newJpaUser, userDetails.getPassword());
            userRepository.save(newJpaUser);
            passwordRepository.save(newJpaUserPassword);
        }
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String s) {
        userRepository.deleteByNickname(s);
    }

    @Override
    public void changePassword(String s, String s1) {
        SecurityContext context = SecurityContextHolder.getContext();
        var nickname =  context.getAuthentication().getName();
        var jpaUser = userRepository.findByNickname(nickname);

        passwordRepository.delete(jpaUser.getJpaPassword());
        JpaUserPassword newPassword = new JpaUserPassword(jpaUser, s1);
        jpaUser.setJpaPassword(newPassword);
    }

    @Override
    public boolean userExists(String s) {
        return userRepository.existsByNickname(s);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = userRepository.findByNickname(s);

        if(user == null)
            throw new UsernameNotFoundException("User with this username not found: " + s);

        return new SecurityUser(user);
    }
}
