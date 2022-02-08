package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("login")
@CrossOrigin
@Validated
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWKSource<SecurityContext> jwkSource;
    private final ObjectProvider<JWTClaimsSet> jwtClaimsSetProvider;
    private final Integer keysCount;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           JWKSource<SecurityContext> jwkSource,
                           ObjectProvider<JWTClaimsSet> jwtClaimsSetProvider,
                           @Qualifier("keysCount") Integer keysCount){
        this.authenticationManager = authenticationManager;
        this.jwkSource = jwkSource;
        this.jwtClaimsSetProvider = jwtClaimsSetProvider;
        this.keysCount = keysCount;
    }

    @PostMapping("{nickname}")
    public ResponseEntity<String> handleLogin(@PathVariable String nickname, @NotBlank @RequestParam CharSequence password) throws JOSEException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nickname, password));
        var jws = createJWS(nickname);

        return new ResponseEntity<>(jws.serialize(), HttpStatus.ACCEPTED);
    }

    private JWSObject createJWS(String username) throws JOSEException{
        var jwk = jwkSource.get(new JWKSelector(new JWKMatcher.Builder()
                .algorithm(JWSAlgorithm.HS256)
                .keyID(String.valueOf(ThreadLocalRandom.current().nextInt(keysCount)+1))
                .build()), null).get(0);

        var signer = new MACSigner((OctetSequenceKey) jwk);

        var header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .keyID(jwk.getKeyID())
                .type(JOSEObjectType.JWT)
                .build();

        var claims = jwtClaimsSetProvider.getObject(username);
        JWSObject jwsObject = new JWSObject(header, new Payload(claims.getClaims()));

        jwsObject.sign(signer);

        return jwsObject;
    }

}
