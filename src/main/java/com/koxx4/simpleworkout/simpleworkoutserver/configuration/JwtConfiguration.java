package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Configuration
public class JwtConfiguration {

    @Value("#{${jwt.secret}}")
    private Map<String, byte[]> hmacKeys;

    @Value("${jwt.expiryTime}")
    private long secondsToTokenExpire;

    @Bean
    public JWKSource<SecurityContext> jwkSource() {

        if (hmacKeys.isEmpty())
            throw new IllegalStateException("Keys for signing JWS tokens cannot be empty!");

        List<JWK> keys = new ArrayList<>();

        for(var hmacKeyPairs : hmacKeys.entrySet()) {

            keys.add(new OctetSequenceKey
                    .Builder(hmacKeyPairs.getValue())
                    .algorithm(JWSAlgorithm.HS256)
                    .keyID(hmacKeyPairs.getKey())
                    .build());
        }

        JWKSet keySet = new JWKSet(keys);

        return new ImmutableJWKSet<>(keySet);
    }

    @Bean
    public Integer keysCount(){
        return hmacKeys.keySet().size();
    }

    @Bean
    @Scope("prototype")
    public JWTClaimsSet jwtClaimsSet(String username){
        //Using manual "iat" and "exp" because
        // api uses util.Date and converts it into formatted string
        // instead of embedding epoch time integer
        return new JWTClaimsSet.Builder()
                .claim("username", username)
                .claim("iat", Instant.now().getEpochSecond())
                .claim("exp", Instant.now().plus(secondsToTokenExpire, ChronoUnit.SECONDS).getEpochSecond())
                .issuer("sws-server")
                .build();
    }

    @Bean
    public ConfigurableJWTProcessor<SecurityContext> contextConfigurableJWTProcessor(){
        // Create a JWT processor for the access tokens
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor =
                new DefaultJWTProcessor<>();

        jwtProcessor.setJWSTypeVerifier(
                new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType("jwt")));


        JWSKeySelector<SecurityContext> keySelector =
                new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, jwkSource());

        jwtProcessor.setJWSKeySelector(keySelector);

        jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier(
                new JWTClaimsSet.Builder()
                        .issuer("sws-server")
                        .build(),
                new HashSet<>(Arrays.asList("iat", "exp", "username"))));

        return jwtProcessor;
    }


}
