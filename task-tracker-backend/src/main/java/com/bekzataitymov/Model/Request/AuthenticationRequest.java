package com.bekzataitymov.Model.Request;

import lombok.*;

@AllArgsConstructor
@Getter
public class AuthenticationRequest {
    private String email;
    private String password;
}
