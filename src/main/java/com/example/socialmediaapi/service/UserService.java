package com.example.socialmediaapi.service;

import com.example.socialmediaapi.entity.Follow;
import com.example.socialmediaapi.entity.User;
import com.example.socialmediaapi.repository.FollowRepository;
import com.example.socialmediaapi.repository.UsersRepository;
import com.example.socialmediaapi.request.UserRequest;
import com.example.socialmediaapi.response.CustomResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository userRepository;
    private final FollowRepository followRepository;

    public UserService(UsersRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public CustomResponse<Object> getAll(){
        List<User> user = userRepository.findAll();
        return new CustomResponse<>(true, false, user, HttpStatus.OK.value());
    }
    public User getResponseById(int id){
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public User getByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user;
    }

    public List<Follow> getUserFollowing(int userId){
        List<Follow> follows = followRepository.findAllByUser_Id(userId);

        return follows;
    }

    public boolean isFollowing(int userId,int followingId){
        Optional<Follow> follow = followRepository.findByUser_IdAndFollowing_Id(userId,followingId);
        return follow.isPresent();
    }

    public User getById(int id){
        return userRepository.findById(id).get();
    }
    public User add(UserRequest userRequest){
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        return userRepository.save(user);
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }
}

