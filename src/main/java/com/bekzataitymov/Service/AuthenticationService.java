package com.bekzataitymov.Service;

import com.bekzataitymov.Exception.CustomException.CredentialsExistsException;
import com.bekzataitymov.Exception.CustomException.NotFound;
import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Response.JwtResponse;
import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private JwtService jwtService;
    private UserRepository userRepository;
    private KafkaProducer kafkaProducer;
    private String jwt = "";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public AuthenticationService(JwtService jwtService, UserRepository userRepository, KafkaProducer kafkaProducer){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public JwtResponse signUp(AuthenticationRequest authRequest) {
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(authRequest.getPassword()));
        Optional<User> optionalUser = userRepository.findByEmail(authRequest.getEmail());
        if(optionalUser.isPresent()){
            throw new CredentialsExistsException("Email is busy.");
        }
        userRepository.save(user);
        jwt = jwtService.generateToken(authRequest.getEmail());
        JwtResponse response = new JwtResponse(jwt);

        kafkaProducer.sendMessage("Hello, welcome to " + optionalUser.get().getEmail());
        return response;
    }

    public JwtResponse signIn(AuthenticationRequest authRequest){
        Optional<User> optionalUser = userRepository.findByEmail(authRequest.getEmail());
        if(optionalUser.isEmpty()){
            throw new NotFound("User not found.");
        }
        jwt = jwtService.generateToken(authRequest.getEmail());
        JwtResponse response = new JwtResponse(jwt);
        return response;
    }
}
