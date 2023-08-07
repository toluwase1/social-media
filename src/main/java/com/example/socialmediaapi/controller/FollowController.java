package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.request.FollowRequest;
import com.example.socialmediaapi.response.CustomResponse;
import com.example.socialmediaapi.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Object>> create(@RequestBody FollowRequest followRequest){
        return new ResponseEntity<>(followService.create(followRequest), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<CustomResponse<Object>> delete(@RequestBody FollowRequest followRequest){
        return new ResponseEntity<>(followService.delete(followRequest),HttpStatus.OK);
    }
}
