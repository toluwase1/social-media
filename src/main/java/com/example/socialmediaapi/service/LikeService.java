package com.example.socialmediaapi.service;

import com.example.socialmediaapi.entity.Like;
import com.example.socialmediaapi.repository.LikeRepository;
import com.example.socialmediaapi.request.LikeRequest;
import com.example.socialmediaapi.response.CustomResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository){
        this.likeRepository = likeRepository;
    }
    public CustomResponse<Object> create(LikeRequest likeRequest) {
        try {
            Like like = new Like();
            BeanUtils.copyProperties(likeRequest,like);
            likeRepository.save(like);
            return new CustomResponse<>(true, false, like, HttpStatus.CREATED.value());
        }catch (Exception e) {
            return new CustomResponse<>(false, true, "like failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public CustomResponse<Object>  getAllByPost(int postId) {
        try {
            List<Like> posts = likeRepository.findAllByPost_Id(postId);
            return new CustomResponse<>(true, false, posts, HttpStatus.OK.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "error occurred while fetching", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public CustomResponse<Object>  getAllByUser(int userId) {
        try {
            List<Like> posts = likeRepository.findAllByUser_Id(userId);
            return new CustomResponse<>(true, false, posts, HttpStatus.OK.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "error occurred while fetching", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public CustomResponse<Object>  isLiked(int userId, int postId) {
        Optional<Like> like = likeRepository.findByUser_IdAndPost_Id(userId,postId);
        if (like.isPresent()){
            return new CustomResponse<>(true, false, "post liked", HttpStatus.OK.value());
        }
        return new CustomResponse<>(true, false, "post not liked", HttpStatus.OK.value());
    }

    public CustomResponse<Object>  delete(LikeRequest likeRequest) {
        Optional<Like> like = likeRepository.findByUser_IdAndPost_Id(likeRequest.getUserId(),likeRequest.getPostId());
        likeRepository.delete(like.get());
        return new CustomResponse<>(true, false, "post unliked", HttpStatus.OK.value());

    }
}
