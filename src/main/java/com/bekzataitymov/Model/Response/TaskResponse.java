package com.bekzataitymov.Model.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TaskResponse {

    @NonNull
    private String header;

    @NonNull
    private String description;

    @NonNull
    private int author_id;

    @NonNull
    private boolean isCompleted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;
}
