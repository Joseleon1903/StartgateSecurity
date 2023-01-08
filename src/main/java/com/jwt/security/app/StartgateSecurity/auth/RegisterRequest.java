package com.jwt.security.app.StartgateSecurity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String name;

    private String email;

    private String username;

    private String password;

    private LocalDateTime lastUpadate = LocalDateTime.now();


}
