package com.example.socialmediaapi.config;

import com.example.socialmediaapi.entity.User;
import com.example.socialmediaapi.repository.AuthenticationRepository;
import com.example.socialmediaapi.security.CustomUserDetailsService;
import com.example.socialmediaapi.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtTokenProvider {


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Autowired
    private UserService userDetailsServices;

    @Autowired
    private AuthenticationRepository userRepository;



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

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String email) {
//            Map<String, Object> claims = new HashMap<>();
        return createToken(email);
    }

    private String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return true;
    }

    public Optional<User> resolveUser(HttpServletRequest request) {
        String token = this.resolveToken(request);
        String subject = this.getSubject(token);
        System.out.println(subject);
        Optional<User> user  = Optional.ofNullable(userRepository.findByEmail(subject));
        if(user.isEmpty()){
            return Optional.ofNullable(userRepository.findByEmail(subject));
        }
        return  user;
    }


    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}

