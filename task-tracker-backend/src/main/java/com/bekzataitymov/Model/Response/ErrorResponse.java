package com.bekzataitymov.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String message;
}
