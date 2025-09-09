package com.bekzataitymov.Model.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class TaskResponse {

    private String header;

    private String description;

    private int author_id;

    private boolean isCompleted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;
}
