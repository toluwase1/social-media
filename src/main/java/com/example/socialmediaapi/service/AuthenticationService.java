package com.example.socialmediaapi.service;


import com.example.socialmediaapi.config.JwtService;
import com.example.socialmediaapi.entity.Token;
import com.example.socialmediaapi.entity.TokenType;
import com.example.socialmediaapi.entity.User;
import com.example.socialmediaapi.repository.TokenRepository;
import com.example.socialmediaapi.repository.UsersRepository;
import com.example.socialmediaapi.request.RegisterRequest;
import com.example.socialmediaapi.response.CustomResponse;
import com.example.socialmediaapi.security.AuthenticationRequest;
import com.example.socialmediaapi.security.AuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationService {
  private final UsersRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(UsersRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
    this.repository = repository;
    this.tokenRepository = tokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public CustomResponse<AuthenticationResponse> register(RegisterRequest request) {
    try {
      var user = User.builder().name(request.getName()).lastName(request.getLastName()).email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword())).build();
      var savedUser = repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);
      saveUserToken(savedUser, jwtToken);
      var response =AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
      return new CustomResponse<>(true, false, response, HttpStatus.CREATED.value());
    } catch (Exception e) {
      return new CustomResponse<>(false, true, "invalid request (email exist)", HttpStatus.BAD_REQUEST.value());
    }
  }

  public CustomResponse<AuthenticationResponse> authenticate(AuthenticationRequest request) {
    try {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    Optional<User> user = repository.findByEmail(request.getEmail());
    var jwtToken = jwtService.generateToken(user.get());
    var refreshToken = jwtService.generateRefreshToken(user.get());
    revokeAllUserTokens(user.get());
    saveUserToken(user.get(), jwtToken);
    var response = AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
      return new CustomResponse<>(true, false, response, HttpStatus.OK.value());
    } catch (Exception e) {
      return new CustomResponse<>(false, true, "invalid credentials", HttpStatus.CREATED.value());
    }
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      Optional<User> user = repository.findByEmail(userEmail);
      if (jwtService.isTokenValid(refreshToken,  user.get())) {
        var accessToken = jwtService.generateToken(user.get());
        revokeAllUserTokens(user.get());
        saveUserToken(user.get(), accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
