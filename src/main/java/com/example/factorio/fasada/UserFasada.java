package com.example.factorio.fasada;


import com.example.factorio.model.AuthenticationRequest;
import com.example.factorio.model.AuthenticationResponse;
import com.example.factorio.model.RegistraionRequest;
import com.example.factorio.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base.path}/auth")
@RequiredArgsConstructor
public class UserFasada {

    private final AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistraionRequest registraionRequest
    ){
        return ResponseEntity.ok(authenticationService.register(registraionRequest));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION,"Bearer "+authenticationResponse.getToken());
        httpHeaders.add(HttpHeaders.AUTHORIZATION,"Bearer "+authenticationResponse.getToken());
        return  ResponseEntity.ok()
                .headers(httpHeaders)
                .body(authenticationResponse);
    }





}
