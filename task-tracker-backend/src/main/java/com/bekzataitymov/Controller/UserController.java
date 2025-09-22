package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Response.UserResponse;
import com.bekzataitymov.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public UserResponse getCurrentUser(){
        return userService.getCurrentUser();
    }


}
