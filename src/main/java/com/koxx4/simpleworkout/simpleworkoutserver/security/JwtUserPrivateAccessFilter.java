package com.koxx4.simpleworkout.simpleworkoutserver.security;

import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchAppUserException;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class JwtUserPrivateAccessFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;
    private final AppUserRepository userRepository;

    public JwtUserPrivateAccessFilter(ConfigurableJWTProcessor<SecurityContext> jwtProcessor,
                                      AppUserRepository userRepository) {
        this.jwtProcessor = jwtProcessor;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String encodedAuth = request.getHeader("authorization");

        if (encodedAuth == null || !encodedAuth.startsWith("Bearer")) {
            failRoutine(response);
            return;
        }

        var encodedToken = encodedAuth.replace("Bearer ", "");

        try{
            var claims  = jwtProcessor.process(encodedToken, null);
            var username = (String) claims.getClaim("username");
            var foundAppUser = userRepository.findByNickname(username)
                    .orElseThrow(NoSuchAppUserException::new);

            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            foundAppUser.getNickname(),
                            foundAppUser.getPassword().getPassword(),
                            foundAppUser.getRoles()));

        } catch (NoSuchAppUserException | ParseException | JOSEException | BadJOSEException e){
            logger.error(e.getMessage());
            failRoutine(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void failRoutine(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println("Token is not valid");
    }

}
