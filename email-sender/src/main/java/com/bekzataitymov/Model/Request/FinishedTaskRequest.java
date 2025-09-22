package com.bekzataitymov.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FinishedTaskRequest {
    private String message;
    private List<String> header;
}
