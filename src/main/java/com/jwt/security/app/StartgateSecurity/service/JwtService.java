package com.jwt.security.app.StartgateSecurity.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String jwToken);

    String generatedToken(UserDetails userDetails);

    String generatedToken(Map<String, Objects> extraClaims, UserDetails userDetails);

    boolean isTokenValid(String jwToken,UserDetails userDetails);

    boolean isTokenExpired(String jwToken);

    public <T> T extractClaim(String jwToken, Function<Claims, T> claimsResolver);


}