package com.bekzataitymov.Service;

import com.bekzataitymov.Exception.CustomException.AlreadyExistsException;
import com.bekzataitymov.Exception.CustomException.NotFoundException;
import com.bekzataitymov.Model.Request.TaskRequest;
import com.bekzataitymov.Model.Response.TaskResponse;
import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Repository.TaskRepository;
import com.bekzataitymov.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse create(TaskRequest taskRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        Optional<Task> optionalTask = taskRepository.findByHeader(taskRequest.getHeader());
        if(optionalTask.isPresent()){
            throw new AlreadyExistsException("Such header already exists");
        }

        Task task = new Task(taskRequest.getHeader(), taskRequest.getDescription(), optionalUser.get(), false);
        taskRepository.save(task);
        TaskResponse taskResponse = new TaskResponse(task.getHeader(), task.getDescription(), task.getUser().getId(),
                task.isCompleted(), null);
        return taskResponse;
    }

    public TaskResponse update(int id, TaskRequest taskRequest) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()){
            throw new NotFoundException("Task not found");
        }
        List<Task> tasks = taskRepository.findAllByUserId(optionalTask.get().getUser().getId());
        for(Task task : tasks){
            if(task.getId() != optionalTask.get().getId() &&
                task.getHeader().equals(taskRequest.getHeader())){
                throw new AlreadyExistsException("Such header is already taken.");
            }
        }

        Task task = optionalTask.get();
        boolean isCompleted = false;
        Timestamp timestamp = null;
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

    public List<TaskResponse> getTasks() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found.");
        }

        List<Task> tasks = taskRepository.findAllByUserId(optionalUser.get().getId());
        List<TaskResponse> responses = new ArrayList<>();

        if (tasks.isEmpty()) {
            throw new NotFoundException("Task not found.");
        }
        for (Task task : tasks) {
            TaskResponse taskResponse = new TaskResponse(task.getHeader(), task.getDescription(),
                    task.getUser().getId(), task.isCompleted(), task.getTimestamp());
            responses.add(taskResponse);
        }

        return responses;

    }

    public void delete(int id) {
        taskRepository.deleteById(id);
    }
}
