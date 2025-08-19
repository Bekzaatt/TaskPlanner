package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Request.TaskRequest;
import com.bekzataitymov.Model.Response.TaskResponse;
import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<String> createTask(@RequestBody TaskRequest taskRequest) throws JsonProcessingException {
        return ResponseEntity.ok(new ObjectMapper().writeValueAsString(taskService.create(taskRequest)));
    }

    @PutMapping("/task")
    public ResponseEntity<String> updateTask(@RequestBody TaskRequest taskRequest) throws JsonProcessingException {
        return ResponseEntity.ok(new ObjectMapper().writeValueAsString(taskService.update(taskRequest)));
    }

    @DeleteMapping("/task")
    public ResponseEntity<Void> deleteTask(@RequestParam String header){
        taskService.delete(header);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
