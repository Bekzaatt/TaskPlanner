package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.JwtResponse;
import com.bekzataitymov.Service.AuthenticationService;
import com.bekzataitymov.Producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final KafkaProducer kafkaProducer;

    @PostMapping("/user")
    public JwtResponse signUp(@RequestBody AuthenticationRequest authRequest){
        return authenticationService.signUp(authRequest);
    }

    @PostMapping("/auth/login")
    public JwtResponse signIn(@RequestBody AuthenticationRequest authRequest){
        return authenticationService.signIn(authRequest);
    }

}
