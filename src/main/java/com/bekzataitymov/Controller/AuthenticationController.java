package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.JwtResponse;
import com.bekzataitymov.Service.AuthenticationService;
import com.bekzataitymov.Service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    private AuthenticationService authenticationService;

    private KafkaProducer kafkaProducer;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, KafkaProducer kafkaProducer){
        this.authenticationService = authenticationService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/user")
    public ResponseEntity<JwtResponse> signUp(@RequestBody AuthenticationRequest authRequest){
        return ResponseEntity.ok(authenticationService.signUp(authRequest));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> signIn(@RequestBody AuthenticationRequest authRequest){
        return ResponseEntity.ok(authenticationService.signIn(authRequest));
    }

}
