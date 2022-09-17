package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.AppUserDetailsService;
import com.koxx4.simpleworkout.simpleworkoutserver.security.JwtUserPrivateAccessFilter;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Profile(value = "dev")
@Configuration
public class DevSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserPasswordRepository passwordRepository;

    @Autowired
    private AppUserDetailsService appUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    FilterRegistrationBean<JwtUserPrivateAccessFilter> userDataRestFilter(UserService userService) {

        FilterRegistrationBean<JwtUserPrivateAccessFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        try {

            filterRegistrationBean.setFilter(new JwtUserPrivateAccessFilter(userService));

            filterRegistrationBean.addUrlPatterns("/user/*");

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

        //For H2 Console proper rendering
        http.headers().frameOptions().sameOrigin();

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeRequests().mvcMatchers("/register/**").permitAll()
                .mvcMatchers("/login/**").permitAll()
                .antMatchers("/data/*").hasRole("ADMIN")
                .anyRequest().permitAll();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(appUserDetailsService);
    }
}

