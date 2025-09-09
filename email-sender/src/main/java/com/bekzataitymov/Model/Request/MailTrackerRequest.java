package com.bekzataitymov.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailTrackerRequest {
    private String email;
    private FinishedTaskRequest finishedTaskRequest;
    private UnfinishedTaskRequest unfinishedTaskRequest;
}
