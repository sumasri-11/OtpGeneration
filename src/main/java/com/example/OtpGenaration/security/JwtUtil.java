package com.example.OtpGenaration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKeymySecretKeymySecretKeymySecretKey";//this is secret key without this can't generate jwt
    private static final long Expiration = 1000 * 60 * 60;//expiration time(1000ms=1sec,60sec=1min,60min=1hour)

    private Key getSigningKey() {//private helper method
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());//converts string to Key
    }

 public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
//                .claim("userId",userId)
//                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+Expiration))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();

 }
 private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
 }
 public String extractUsername(String token) {
        return extractClaims(token).getSubject();
 }
//public String extractRole(String token) {
//        return extractClaims(token).get("role",String.class);
//}
//    public Long extractUserId(String token) {
//        return extractClaims(token).get("userId",Long.class);
//    }
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
public boolean validateToken(
        String token,String email
){
        return extractUsername(token).equals(email)&&!isTokenExpired(token);
}

    }

