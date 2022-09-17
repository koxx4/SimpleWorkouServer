package com.koxx4.simpleworkout.simpleworkoutserver.security;

import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.UnauthorizedRequestException;
import com.koxx4.simpleworkout.simpleworkoutserver.services.UserService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.koxx4.simpleworkout.simpleworkoutserver.services.RequestUtils.extractJwtToken;
import static org.slf4j.LoggerFactory.getLogger;

public class JwtUserPrivateAccessFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = getLogger(JwtUserPrivateAccessFilter.class);

    private final UserService userService;


    public JwtUserPrivateAccessFilter(UserService userService) {

        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String encodedToken = extractJwtToken(request)
                    .orElseThrow(UnauthorizedRequestException::new);

            var appUserAuthenticationTokenDto = userService.getAppUserAuthDtoByJwtToken(encodedToken);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    appUserAuthenticationTokenDto.getNickname(),
                    appUserAuthenticationTokenDto.getPassword(),
                    appUserAuthenticationTokenDto.getRoles()));

        } catch (Exception e) {

            LOGGER.error(e.getMessage());
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
