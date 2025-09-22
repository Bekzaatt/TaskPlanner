package com.bekzataitymov.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UnfinishedTaskRequest {
    private String message;
    private List<String> header;
}
