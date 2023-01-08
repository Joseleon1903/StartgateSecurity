package com.jwt.security.app.StartgateSecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{

    private static final String SECRET_KEY = "28472B4B6250655368566D597133743677397A244326462948404D635166546A";

    @Override
    public String extractUsername(String jwToken) {
        return extractClaim(jwToken, Claims::getSubject);
    }

    private Claims extractAllClaims(String jwToken){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwToken)
                .getBody();
    }

    @Override
    public String generatedToken(UserDetails userDetails){
        return generatedToken(new HashMap<>(), userDetails);

    }

    @Override
    public String generatedToken(Map<String, Objects> extraClaims, UserDetails userDetails){
        System.out.println("Entering generatedToken");
        System.out.println("subject "+userDetails.getUsername());

        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public boolean isTokenValid(String jwToken,UserDetails userDetails){
        final String username = extractUsername(jwToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwToken));

    }

    @Override
    public boolean isTokenExpired(String jwToken) {
        return extractExpiration(jwToken).before(new Date());
    }

    private Date extractExpiration(String jwToken) {
        return extractClaim(jwToken, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String jwToken, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(jwToken);
        return claimsResolver.apply(claims);
    }

    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
