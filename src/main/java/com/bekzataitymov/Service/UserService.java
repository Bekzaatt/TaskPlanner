package com.bekzataitymov.Service;

import com.bekzataitymov.Exception.CustomException.NotFound;
import com.bekzataitymov.Model.Response.TaskResponse;
import com.bekzataitymov.Model.Response.UserResponse;
import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.TaskRepository;
import com.bekzataitymov.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    public UserResponse getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new NotFound("User not found.");
        }
        return new UserResponse(user.get().getId(), user.get().getEmail());
    }

    public List<TaskResponse> getTasks()  {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new NotFound("User not found.");
        }

        List<Task> tasks = taskRepository.findAllByUserId(optionalUser.get().getId());
        List<TaskResponse> responses = new ArrayList<>();

        if(tasks.isEmpty()){
            throw new NotFound("Task not found.");
        }
        for(Task task : tasks){
            TaskResponse taskResponse = new TaskResponse(task.getHeader(), task.getDescription(),
                    task.getUser().getId(), task.isCompleted(), task.getTimestamp());
            responses.add(taskResponse);
        }

        return responses;
    }
}
