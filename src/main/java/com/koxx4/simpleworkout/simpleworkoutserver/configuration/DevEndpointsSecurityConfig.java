package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.JpaUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Profile(value = "dev")
@Configuration
public class DevEndpointsSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JpaPasswordRepository passwordRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsManager = new JpaUserDetailsManager(userRepository, passwordRepository);

        var user1 = User.builder().
                password(passwordEncoder().encode("pass1")).
                roles("USER").
                username("user1").
                build();

        var user2 = User.builder().
                password(passwordEncoder().encode("pass2")).
                roles("ADMIN").
                username("user2").
                build();

        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);

        return userDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().permitAll();
    }


}

