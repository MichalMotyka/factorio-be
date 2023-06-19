package com.example.factorio.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${auth.jwt.secret}")
    private String SECRET;
    @Value("${auth.jwt.token.exp}")
    private long TOKEN_EXP;
    @Value("${auth.jwt.refresh.exp}")
    private long REFRESH_TOKEN_EXP;

    public String extractUsername(String token) {
        return exctractClaim(token,Claims::getSubject);
    }

    public <T> T exctractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return  claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        if (token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(
            Map<String,Object> claims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_EXP))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(
            Map<String,Object> claims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(
            UserDetails userDetails
    ) {
       return generateToken(new HashMap<>(), userDetails);
    }
    public String generateRefreshToken(UserDetails userDetails){
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return exctractClaim(token,Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
