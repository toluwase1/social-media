package com.example.socialmediaapi.service;

import com.example.socialmediaapi.entity.Follow;
import com.example.socialmediaapi.repository.FollowRepository;
import com.example.socialmediaapi.request.FollowRequest;
import com.example.socialmediaapi.response.CustomResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    public FollowService(FollowRepository followRepository, UserService userService){
        this.followRepository = followRepository;
        this.userService = userService;
    }
    public CustomResponse<Object> create(FollowRequest followRequest) {
        if (userService.isFollowing(followRequest.getUserId(), followRequest.getFollowingId())){
            return new CustomResponse<>(false, true, "already followed", HttpStatus.CONFLICT.value())
        }
        Follow follow = new Follow();
        BeanUtils.copyProperties(followRequest,follow);
        followRepository.save(follow);
        return new CustomResponse<>(true, false, "successfully followed", HttpStatus.CREATED.value());
    }

    public CustomResponse<Object>  delete(FollowRequest followRequest) {
        Follow follow = followRepository.findByUser_IdAndFollowing_Id(followRequest.getUserId(), followRequest.getFollowingId()).orElse(null);
        if (follow!=null) {
            followRepository.delete(follow);
            return new CustomResponse<>(true, false, "successfully unfollowed", HttpStatus.CREATED.value());
        }
        return new CustomResponse<>(false, true, "you are not following this user", HttpStatus.CREATED.value());
    }
}
