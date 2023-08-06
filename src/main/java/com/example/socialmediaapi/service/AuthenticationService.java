package com.example.socialmediaapi.service;

import com.example.socialmediaapi.entity.User;
import com.example.socialmediaapi.repository.AuthenticationRepository;
import com.example.socialmediaapi.request.RegisterRequest;
import com.example.socialmediaapi.response.CustomResponse;
import com.example.socialmediaapi.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationRepository authenticationRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationRepository = authenticationRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomResponse<Object> login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            Optional<User> user = Optional.ofNullable(authenticationRepository.findByEmail(email));
            final String jwt = jwtUtil.generateToken(user.get().getName(), user.get().getId(),user.get().getEmail());
            return new CustomResponse<>(true, false, jwt, HttpStatus.OK.value());
        } catch (BadCredentialsException e) {
            return new CustomResponse<>(false, true, "UNAUTHORIZED: Incorrect email or password", HttpStatus.OK.value());
        }
    }

    public CustomResponse<Object> signup(RegisterRequest registerRequest) {
        try {
            if (authenticationRepository.findByEmail(registerRequest.getEmail())!=null){
                return new CustomResponse<>(false, true, "BAD REQUEST: email exist", HttpStatus.BAD_REQUEST.value());
            }
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setName(registerRequest.getName());
            user.setLastName(registerRequest.getLastName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            authenticationRepository.save(user);
            return new CustomResponse<>(true, false, user, HttpStatus.CREATED.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "INTERNAL SERVER ERROR: Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
