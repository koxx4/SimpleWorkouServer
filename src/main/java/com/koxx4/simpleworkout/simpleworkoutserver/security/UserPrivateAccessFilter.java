package com.koxx4.simpleworkout.simpleworkoutserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class UserPrivateAccessFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final int nicknamePosition;

    public UserPrivateAccessFilter(@Autowired AuthenticationManager authenticationManager,
                                   final int nicknamePosition) {
        super();
        this.authenticationManager = authenticationManager;
        this.nicknamePosition = nicknamePosition;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String encodedAuth = request.getHeader("authorization");
        String usernamePathParam = request.getServletPath().split("[/]")[nicknamePosition];

        if (encodedAuth == null) {

            failRoutine(response, HttpStatus.BAD_REQUEST, "User credentials not found");
            return;
        }

        encodedAuth = encodedAuth.replace("Basic ", "");
        CharSequence[] decodedAuth = new String(Base64.getDecoder().decode(encodedAuth)).split("[:]");
        CharSequence user = decodedAuth[0];
        CharSequence password = decodedAuth[1];

        if (!user.equals(usernamePathParam)) {

            failRoutine(response, HttpStatus.BAD_REQUEST, "Invalid user credentials provided.");

            return;
        }


        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));

        } catch (AuthenticationException exception) {

            failRoutine(response, HttpStatus.UNAUTHORIZED, "Invalid user credentials provided.");

            return;
        }

        filterChain.doFilter(request, response);
    }

    private void failRoutine(HttpServletResponse response, HttpStatus status, String msg) throws IOException {

        response.setStatus(status.value());
        response.getWriter().println(msg);
    }


}
