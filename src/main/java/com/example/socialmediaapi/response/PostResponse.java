package com.example.socialmediaapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private int id;
    private int userId;
    private String userName;
    private String userLastName;
    private String Description;
}