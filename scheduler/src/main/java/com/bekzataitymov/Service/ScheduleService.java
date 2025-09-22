package com.bekzataitymov.Service;

import com.bekzataitymov.Model.Request.FinishedTaskRequest;
import com.bekzataitymov.Model.Request.MailRequest;
import com.bekzataitymov.Model.Request.UnfinishedTaskRequest;
import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Model.User;
import com.bekzataitymov.Producer.KafkaProducer;
import com.bekzataitymov.Repository.TaskRepository;
import com.bekzataitymov.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "0 * * * * ?")
    public void taskTracker(){
        log.info("Starting to schedule tasks");
        List<User> users = userRepository.findAll();
        MailRequest mailRequest = null;
        for(User user : users){
            List<Task> tasks = taskRepository.findAllByUserId(user.getId());
            List<String> headers = getHeaders(tasks);
            int countUnfinishedTask = countUnfinishedTasks(tasks);
            int countFinishedTask = counterFinishedTasks(tasks);
            if(countUnfinishedTask == tasks.size()){
                mailRequest =
                        new MailRequest(user.getEmail(), null,
                                new UnfinishedTaskRequest("You have unfinished " +
                                        countUnfinishedTask + " tasks", headers));
            }
            else if(countFinishedTask == tasks.size()){
                mailRequest =
                        new MailRequest(user.getEmail(), new FinishedTaskRequest("You have finished " +
                                countFinishedTask + " tasks", headers), null);
            }else{
                List<String> finishedHeaders = new ArrayList<>();
                List<String> unfinishedHeaders = new ArrayList<>();

                for(Task task : tasks){
                    if(task.isCompleted()){
                        finishedHeaders.add(task.getHeader());
                    }else {
                        unfinishedHeaders.add(task.getHeader());
                    }
                }
                mailRequest =
                        new MailRequest(user.getEmail(),
                                new FinishedTaskRequest("You have finished " + countFinishedTask + " tasks",
                                headers), new UnfinishedTaskRequest("You have unfinished " +
                                countUnfinishedTask + " tasks", headers));
            }
        }
        kafkaProducer.sendMessage(mailRequest);
    }

    public int countUnfinishedTasks(List<Task> tasks){
        int counterUnfinishedTasks = 0;
        for(Task task : tasks){
            if(!task.isCompleted()){
                counterUnfinishedTasks++;
            }
        }
        return counterUnfinishedTasks;
    }

    public int counterFinishedTasks(List<Task> tasks){
        int counterFinishedTasks = 0;
        for(Task task : tasks){
            if(task.isCompleted()){
                counterFinishedTasks++;
            }
        }
        return counterFinishedTasks;
    }

    public List<String> getHeaders(List<Task> tasks){
        List<String> headers = new ArrayList<>();
        for(Task task : tasks){
            headers.add(task.getHeader());
        }
        return headers;
    }
}
