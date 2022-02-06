package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.security.AppUserDetailsService;
import com.koxx4.simpleworkout.simpleworkoutserver.security.JwtUserPrivateAccessFilter;
import com.koxx4.simpleworkout.simpleworkoutserver.security.UserPrivateAccessFilter;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@Profile(value = "production")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppUserPasswordRepository passwordRepository;

    @Value("${jwt.secret}")
    private byte[] signingKey;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.cors().and().csrf().disable();
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeRequests()
                .mvcMatchers("/register/**").permitAll()
                .mvcMatchers("/login/**").permitAll()
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
        auth.userDetailsService(this.userDetailsService());
    }

    @Bean
    FilterRegistrationBean<JwtUserPrivateAccessFilter> userDataRestFilter(){
        FilterRegistrationBean<JwtUserPrivateAccessFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        try {
            filterRegistrationBean.setFilter(new JwtUserPrivateAccessFilter(
                    this.contextConfigurableJWTProcessor(),
                    userRepository));
            filterRegistrationBean.addUrlPatterns("/user/*");
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
        return new AppUserDetailsService(userRepository);
    }

    @Bean
    public ConfigurableJWTProcessor<SecurityContext> contextConfigurableJWTProcessor(){
        // Create a JWT processor for the access tokens
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor =
                new DefaultJWTProcessor<>();

        jwtProcessor.setJWSTypeVerifier(
                new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType("jwt")));

        JWKSource<SecurityContext> keySource =
                new ImmutableSecret<>(this.signingKey);

        JWSKeySelector<SecurityContext> keySelector =
                new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, keySource);

        jwtProcessor.setJWSKeySelector(keySelector);

        jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier(
                new JWTClaimsSet.Builder().build(),
                new HashSet<>(Arrays.asList("iat", "exp"))));

        return jwtProcessor;
    }
}
