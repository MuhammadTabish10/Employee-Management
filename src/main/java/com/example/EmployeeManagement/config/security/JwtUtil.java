package com.example.EmployeeManagement.config.security;

import com.example.EmployeeManagement.dto.CustomUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final String SECRET_KEY = "hjasdkjsbackljsbndg15fd5s61f35ds1gf56asv5fd1sfdsfdsgDVKSDMVDSkfefsadf5sd4f68d4sa68f4ds6f4s5d64ft8ews4f56sde4yh86t44ew3t2421rfdkjasnvfklasvz";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public String generateToken(UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetail customUserDetail) {
            String email = customUserDetail.getEmail();

            Map<String, Object> claims = new HashMap<>();
            List<String> roles = new ArrayList<>();
            List<String> permissions = new ArrayList<>();

            userDetails.getAuthorities().forEach(authority -> {
                String authorityName = authority.getAuthority();
                if (authorityName.startsWith("ROLE_")) {
                    roles.add(authorityName.substring(5));
                } else {
                    permissions.add(authorityName);
                }
            });

            claims.put("ROLES", roles);
            claims.put("PERMISSIONS", permissions);

            return createToken(claims, email);
        } else {
            throw new IllegalArgumentException("Invalid user details provided");
        }
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+10000*60*60*10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, CustomUserDetail customUserDetail) {
        final String email = extractUsername(token);
        return (email.equals(customUserDetail.getEmail()) && !isTokenExpired(token));
    }
}
