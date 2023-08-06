package com.example.socialmediaapi.security;

import lombok.Data;

@Data
public class JwtResponse {
    private String sub;
    private long exp;
    private UserResponse user;
    private long iat;

    @Data
    public static class UserResponse {
        private int id;
        private String fullName;
        private String email;
    }
}

