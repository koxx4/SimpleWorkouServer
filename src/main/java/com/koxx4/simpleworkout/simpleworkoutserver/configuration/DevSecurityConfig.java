package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserRole;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.AppUserDetailsService;
import com.koxx4.simpleworkout.simpleworkoutserver.security.UserPrivateAccessFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;


@Profile(value = "dev")
@Configuration
public class DevSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppUserPasswordRepository passwordRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(userRepository);
    }

    @Bean
    FilterRegistrationBean<UserPrivateAccessFilter> userDataRestFilter(){
        FilterRegistrationBean<UserPrivateAccessFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        try {
            filterRegistrationBean.setFilter(new UserPrivateAccessFilter(authenticationManagerBean(), 3));
            filterRegistrationBean.addUrlPatterns("/user/actions/*");
            filterRegistrationBean.setOrder(1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return filterRegistrationBean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.cors().and().csrf().disable();
        http.authorizeRequests().mvcMatchers("/register/**").permitAll()
                .antMatchers("/data/*").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        AppUser appUser = new AppUser("example@ex.com", "user1");
        AppUserPassword password = new AppUserPassword(appUser, passwordEncoder().encode("pass1"));
        appUser.setPassword(password);
        appUser.addRole(new UserRole("USER"));
        appUser.addWorkout(new UserWorkout(UserWorkout.WorkoutType.RUNNING, new Date(), "note", 2000d));
        userRepository.save(appUser);

        var user2 = User.builder().
                password(passwordEncoder().encode("pass2")).
                roles("ADMIN").
                username("user2").
                build();
        auth.inMemoryAuthentication().withUser(user2);
        auth.userDetailsService(this.userDetailsService());
    }
}

