package com.jwt.security.app.StartgateSecurity.auth;

import com.jwt.security.app.StartgateSecurity.domain.Role;
import com.jwt.security.app.StartgateSecurity.domain.User;
import com.jwt.security.app.StartgateSecurity.repository.UserRepository;
import com.jwt.security.app.StartgateSecurity.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .lastUpdateDate(request.getLastUpadate())
                .role(Role.SIMPLE_USER)
                .build();

        userRepository.save(user);

        var jwToken = jwtService.generatedToken(user);
        return AuthenticationResponse.builder()
                .token(jwToken)
                .build();
    }

    public AuthenticationResponse autenticate(AuthenticationRequest request) {

        System.out.println("username : "+request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        System.out.println("user found : "+user);
        var jwToken = jwtService.generatedToken(user);
        System.out.println("token generated : "+jwToken);
        return AuthenticationResponse.builder()
                .token(jwToken)
                .build();
    }


}
