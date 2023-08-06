package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.entity.User;
import com.example.socialmediaapi.request.LoginRequest;
import com.example.socialmediaapi.request.RegisterRequest;
import com.example.socialmediaapi.response.CustomResponse;
import com.example.socialmediaapi.security.JwtUtil;
import com.example.socialmediaapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public ResponseEntity<CustomResponse<Object>> login(@RequestBody LoginRequest loginRequest)  {
        return ResponseEntity.ok(authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomResponse<Object>> signup(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.signup(registerRequest));
    }

}
