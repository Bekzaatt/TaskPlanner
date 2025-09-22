package com.bekzataitymov.Service;

import com.bekzataitymov.Exception.CustomException.AlreadyExistsException;
import com.bekzataitymov.Exception.CustomException.NotFoundException;
import com.bekzataitymov.Model.Request.AuthenticationRequest;
import com.bekzataitymov.Model.Request.MailRequest;
import com.bekzataitymov.Model.Response.JwtResponse;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Producer.KafkaProducer;
import com.bekzataitymov.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public AuthenticationService(JwtService jwtService, UserRepository userRepository, KafkaProducer kafkaProducer){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public JwtResponse signUp(AuthenticationRequest authRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(authRequest.getEmail());
        if(optionalUser.isPresent()){
            throw new AlreadyExistsException("Email is already taken.");
        }
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(authRequest.getPassword()));
        userRepository.save(user);
        JwtResponse response = new JwtResponse(jwtService.generateToken(authRequest.getEmail()));
        MailRequest message = new MailRequest(user.getEmail(), "Greeting", "Welcome to " + user.getEmail());
        kafkaProducer.sendMessage(message);
        return response;
    }

    public JwtResponse signIn(AuthenticationRequest authRequest){
        Optional<User> optionalUser = userRepository.findByEmail(authRequest.getEmail());
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User not found.");
        }
        JwtResponse response = new JwtResponse(jwtService.generateToken(authRequest.getEmail()));
        MailRequest message = new MailRequest(optionalUser.get().getEmail(), "Greeting",
                "Welcome to " + optionalUser.get().getEmail());
        kafkaProducer.sendMessage(message);
        return response;
    }
}
