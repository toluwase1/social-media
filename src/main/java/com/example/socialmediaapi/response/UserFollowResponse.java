package com.example.socialmediaapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowResponse {
    private int userId;
    private String name;
    private String lastName;
}
