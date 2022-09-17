package com.koxx4.simpleworkout.simpleworkoutserver.controllers;

import com.koxx4.simpleworkout.simpleworkoutserver.configuration.SpringFoxConfig;
import com.koxx4.simpleworkout.simpleworkoutserver.services.AppUserJwtService;
import com.nimbusds.jose.JOSEException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("login")
@CrossOrigin
@Validated
@Api(tags = {SpringFoxConfig.LOGIN_CONTROLLER_TAG})
class LoginController {

    private final AuthenticationManager authenticationManager;
    private final AppUserJwtService appUserJwtService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, AppUserJwtService appUserJwtService) {

        this.authenticationManager = authenticationManager;
        this.appUserJwtService = appUserJwtService;
    }

    @ApiOperation("Authorizes user against database and generates JWS token if user is valid")
    @PostMapping("{nickname}")
    public ResponseEntity<String> handleLogin(@PathVariable String nickname, @NotBlank @RequestParam CharSequence password) throws JOSEException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nickname, password));

        String jws = appUserJwtService.createJwsWithUsername(nickname);

        return new ResponseEntity<>(jws, HttpStatus.OK);
    }
}
