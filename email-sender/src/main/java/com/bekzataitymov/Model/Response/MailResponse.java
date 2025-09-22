package com.bekzataitymov.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MailResponse {
    private String receivedEmail;
    private String message;
}
