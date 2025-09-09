package com.bekzataitymov.Controller;

import com.bekzataitymov.Model.Request.TaskRequest;
import com.bekzataitymov.Model.Response.TaskResponse;
import com.bekzataitymov.Service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task")
    public TaskResponse createTask(@RequestBody TaskRequest taskRequest) {
        return taskService.create(taskRequest);
    }

    @PutMapping("/task/{id}")
    public TaskResponse updateTask(@PathVariable(value = "id") int id,
                                                   @RequestBody TaskRequest taskRequest) {
        return taskService.update(id, taskRequest);
    }

    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks(){
        return taskService.getTasks();
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(value = "id") int id){
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
