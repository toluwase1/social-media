package com.example.socialmediaapi.config;

import com.example.socialmediaapi.response.UserJwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public UserJwtResponse decodeJwtToken(String token) {
        UserJwtResponse jwtModel = new UserJwtResponse();

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            Claims claims = jws.getBody();

            jwtModel.setId(Math.toIntExact(claims.get("user.id", Long.class)));
            jwtModel.setFullName(claims.get("user.fullName", String.class));
            jwtModel.setEmail(claims.getSubject());

            return jwtModel;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("Malformed JWT token: " + e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("JWT token has expired: " + e.getMessage());
        } catch (io.jsonwebtoken.JwtException e) {
            System.out.println("Error decoding JWT token: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return null;
    }
}