package com.koxx4.simpleworkout.simpleworkoutserver.security;

import org.springframework.beans.factory.annotation.Autowired;
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

public class UserDataRestFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public UserDataRestFilter(@Autowired AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");

        if ( nickname == null && email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String encodedAuth = request.getHeader("authorization");

        if (encodedAuth == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        //TODO: pass should not be stored in string
        encodedAuth = encodedAuth.replace("Basic ", "");
        String[] decodedAuth = new String(Base64.getDecoder().decode(encodedAuth)).split("[:]");
        String user = decodedAuth[0];
        String password = decodedAuth[1];

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
        } catch (AuthenticationException exception){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Invalid user credentials provided.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
