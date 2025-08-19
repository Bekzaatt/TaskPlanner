package com.bekzataitymov.Service;

import com.bekzataitymov.Exception.CustomException.NotFound;
import com.bekzataitymov.Model.Request.TaskRequest;
import com.bekzataitymov.Model.Response.TaskResponse;
import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.TaskRepository;
import com.bekzataitymov.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse create(TaskRequest taskRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Task task = new Task(taskRequest.getHeader(), taskRequest.getDescription(), optionalUser.get(), false);
        taskRepository.save(task);
        TaskResponse taskResponse = new TaskResponse(task.getHeader(), task.getDescription(), task.getUser().getId(),
                task.isCompleted());
        return taskResponse;
    }

    public TaskResponse update(TaskRequest taskRequest) {
        Optional<Task> optionalTask = taskRepository.findByHeader(taskRequest.getHeader());
        if(optionalTask.isEmpty()){
            throw new NotFound("Task not found");
        }
        Task task = optionalTask.get();
        boolean isCompleted = false;
        Timestamp timestamp = null;
        System.out.println(taskRequest.isCompleted());
        if(taskRequest.isCompleted()){
            isCompleted = true;
            timestamp = Timestamp.valueOf(LocalDateTime.now());
        }
        Task updatedTask = new Task(task.getId(), taskRequest.getHeader(), taskRequest.getDescription(), task.getUser(),
                isCompleted, timestamp);
        taskRepository.save(updatedTask);
        TaskResponse taskResponse = new TaskResponse(updatedTask.getHeader(), updatedTask.getDescription(),
                updatedTask.getUser().getId(), updatedTask.isCompleted(), updatedTask.getTimestamp());
        return taskResponse;
    }

    public void delete(String header) {
//        Optional<Task> task = taskRepository.findByHeader(header);
//        if(task.isEmpty()){
//            throw new NotFound("Task not found.");
//        }

        taskRepository.delete(header);
    }
}
