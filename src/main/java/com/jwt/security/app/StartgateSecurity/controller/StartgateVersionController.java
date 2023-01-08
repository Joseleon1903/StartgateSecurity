package com.jwt.security.app.StartgateSecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/version")
@RequiredArgsConstructor
public class StartgateVersionController {

    @GetMapping
    public ResponseEntity<String> getVersion(){
        return ResponseEntity.ok("Startgate Version 0.0.1");
    }

}
