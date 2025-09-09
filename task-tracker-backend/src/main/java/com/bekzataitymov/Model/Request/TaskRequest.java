package com.bekzataitymov.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class TaskRequest {
    private String header;
    private String description;
    private boolean isCompleted;
}
