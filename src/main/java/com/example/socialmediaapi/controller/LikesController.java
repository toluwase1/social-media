package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.request.LikeRequest;
import com.example.socialmediaapi.response.CustomResponse;
import com.example.socialmediaapi.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
public class LikesController {
    private final LikeService likeService;

    public LikesController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Object>>  add(@RequestBody LikeRequest likeRequest){
        return new ResponseEntity<>(likeService.create(likeRequest), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CustomResponse<Object>> getAllByPost(@PathVariable int postId){
        return new ResponseEntity<>(likeService.getAllByPost(postId),HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<CustomResponse<Object>>  getAllByUser(@PathVariable int userId){
        return new ResponseEntity<>(likeService.getAllByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/isLiked")
    public ResponseEntity<CustomResponse<Object>>  isLiked(@RequestParam int userId,@RequestParam int postId){
        return new ResponseEntity<>(likeService.isLiked(userId,postId),HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<CustomResponse<Object>>  delete(@RequestBody LikeRequest likeRequest){

        return new ResponseEntity<>( likeService.delete(likeRequest),HttpStatus.OK);
    }
}
