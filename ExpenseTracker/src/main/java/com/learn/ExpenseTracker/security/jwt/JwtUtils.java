package com.learn.ExpenseTracker.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;



@Component
public class JwtUtils {
    @Value("${jwt.expirationms}")
    private int jwtExpirationMs;
    @Value(("${jwt.secret}"))
    private String jwtSecret;

    public String generateToken(UserDetails userDetail) {
        String roles = userDetail.getAuthorities()
                .stream().map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .subject(userDetail.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(jwt);
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return  false;
        }
    }

    public String getUsernameFromToken(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload().getSubject();
    }

    public String getJwtFromHeaders(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken != null && jwtToken.startsWith("Bearer")){
            jwtToken = jwtToken.substring(7);
            return jwtToken;
        }
        return  null;
    }
}
