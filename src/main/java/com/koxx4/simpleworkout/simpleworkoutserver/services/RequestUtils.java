package com.koxx4.simpleworkout.simpleworkoutserver.services;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public class RequestUtils {

    public static final String AUTHORIZATION_HEADER_NAME = "authorization";


    private RequestUtils() {}


    public static Optional<String> extractJwtToken(HttpServletRequest request) {

        Assert.notNull(request, "request must not be null");

        return extractAuthorizationHeader(request)
                .filter(RequestUtils::isBearerToken)
                .map(RequestUtils::getJwtToken);
    }

    public static Optional<String> extractAuthorizationHeader(HttpServletRequest request) {

        Assert.notNull(request, "request must not be null");

        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER_NAME));
    }

    private static String getJwtToken(String token) {

        return token.replace("Bearer ", "");
    }

    private static boolean isBearerToken(String token) {

        return token.startsWith("Bearer");
    }
}
