package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@RestController
@RequestMapping("login")
@CrossOrigin
@Validated
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final byte[] signingKey;
    private final long jwtExpiryTime;

    public LoginController(@Autowired AuthenticationManager authenticationManager,
                           @Value("${jwt.secret}") byte[] signingKey,
                           @Value("${jwt.expiryTime}") long jwtExpiryTime){
        this.authenticationManager = authenticationManager;
        this.signingKey = signingKey;
        this.jwtExpiryTime = jwtExpiryTime;
    }

    @PostMapping("{nickname}")
    public ResponseEntity<String> handleLogin(@PathVariable String nickname, @NotBlank @RequestParam CharSequence password) throws JOSEException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nickname, password));
        var jws = createJWS(nickname, jwtExpiryTime);

        return new ResponseEntity<>(jws.serialize(), HttpStatus.ACCEPTED);
    }

    private JWSObject createJWS(String username, long secondsExpiry) throws JOSEException{
        var signer = new MACSigner(signingKey);
        var header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();

        JWSObject jwsObject = new JWSObject(header,
                new Payload(Map.of("username", username, "iat", System.currentTimeMillis()/1000L, "exp",
                        System.currentTimeMillis()/1000L + secondsExpiry)));

        jwsObject.sign(signer);

        return jwsObject;
    }

}
