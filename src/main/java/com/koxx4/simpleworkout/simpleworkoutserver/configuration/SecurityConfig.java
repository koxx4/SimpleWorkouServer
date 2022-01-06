package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.JpaUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.JpaUserDetailsManager;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile(value = "production")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JpaPasswordRepository passwordRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().antMatchers("/register/*").permitAll()
                .antMatchers("/data/*").hasRole("admin")
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService());
    }

    @Bean
    FilterRegistrationBean<UserPrivateAccessFilter> userDataRestFilter(){
        FilterRegistrationBean<UserPrivateAccessFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        try {
            filterRegistrationBean.setFilter(new UserPrivateAccessFilter(authenticationManagerBean()));
            filterRegistrationBean.addUrlPatterns("/userPrivate/*");
            filterRegistrationBean.setOrder(1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return filterRegistrationBean;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        return new JpaUserDetailsManager(userRepository, passwordRepository);
    }


}
