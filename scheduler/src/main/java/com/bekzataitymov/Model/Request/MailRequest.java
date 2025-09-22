package com.bekzataitymov.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MailRequest {
    private String email;
    private FinishedTaskRequest finishedTasks;
    private UnfinishedTaskRequest unfinishedTasks;
}
