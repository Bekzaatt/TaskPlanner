package com.bekzataitymov.Service;

import com.bekzataitymov.Exception.CustomException.NotFoundException;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Model.UserPrincipal;
import com.bekzataitymov.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User not found.");
        }
        return new UserPrincipal(userOptional.get());
    }
}
