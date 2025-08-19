package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Response.TaskResponse;
import com.bekzataitymov.Model.Response.UserResponse;
import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Service.KafkaProducer;
import com.bekzataitymov.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks(){

         return ResponseEntity.ok(userService.getTasks());
    }
}
