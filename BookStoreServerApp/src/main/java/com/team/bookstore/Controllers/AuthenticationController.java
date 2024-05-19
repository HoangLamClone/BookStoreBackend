package com.team.bookstore.Controllers;

import com.nimbusds.jose.JOSEException;
import com.team.bookstore.Dtos.Requests.IntrospectRequest;
import com.team.bookstore.Dtos.Requests.LogoutRequest;
import com.team.bookstore.Dtos.Requests.RefreshTokenRequest;
import com.team.bookstore.Dtos.Requests.UsernameLoginRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.AuthenticationResponse;
import com.team.bookstore.Dtos.Responses.IntrospectResponse;
import com.team.bookstore.Services.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/login")
    ResponseEntity<APIResponse<?>> authenticate(@RequestBody UsernameLoginRequest usernameLoginRequest) throws AuthenticationException {
        AuthenticationResponse result =
                authenticationService.authenticate(usernameLoginRequest);
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result
        ).build());
    }
    @PostMapping("/introspect")
    ResponseEntity<APIResponse<?>> introspect(@RequestBody IntrospectRequest introspectRequest) throws AuthenticationException, ParseException, JOSEException {
        IntrospectResponse result =
                authenticationService.introspect(introspectRequest);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result
        ).build());
    }
    @PostMapping("/logout")
    ResponseEntity<APIResponse<?>> logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return ResponseEntity.ok(APIResponse.builder().code(200).message(
                "OK").result("Success!").build());
    }
    @PostMapping("/refresh")
    ResponseEntity<APIResponse<?>> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(authenticationService.refreshToken(refreshTokenRequest)).build());
    }
}
