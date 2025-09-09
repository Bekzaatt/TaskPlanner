package com.bekzataitymov.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MailGreetingRequest {
    private String email;
    private String header;
    private String message;
}
