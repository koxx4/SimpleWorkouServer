package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class AppUserJwtService {


    private final JWKSource<SecurityContext> jwkSource;
    private final ObjectProvider<JWTClaimsSet> jwtClaimsSetProvider;
    private final Integer keysCount;

    @Autowired
    public AppUserJwtService(
            JWKSource<SecurityContext> jwkSource,
            ObjectProvider<JWTClaimsSet> jwtClaimsSetProvider,
            @Qualifier("keysCount") Integer keysCount) {

        this.jwkSource = jwkSource;
        this.jwtClaimsSetProvider = jwtClaimsSetProvider;
        this.keysCount = keysCount;
    }


    public String createJwsWithUsername(String username) throws JOSEException {

        var jwk = jwkSource.get(new JWKSelector(new JWKMatcher.Builder()
                .algorithm(JWSAlgorithm.HS256)
                .keyID(String.valueOf(ThreadLocalRandom.current().nextInt(keysCount) + 1))
                .build()), null).get(0);

        MACSigner signer = new MACSigner((OctetSequenceKey) jwk);

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .keyID(jwk.getKeyID())
                .type(JOSEObjectType.JWT)
                .build();

        JWTClaimsSet claims = jwtClaimsSetProvider.getObject(username);

        JWSObject jwsObject = new JWSObject(header, new Payload(claims.getClaims()));

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }
}
