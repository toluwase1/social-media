package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.config.JwtService;
import com.example.socialmediaapi.request.PostRequest;
import com.example.socialmediaapi.response.CustomResponse;
import com.example.socialmediaapi.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/post")
public class PostsController {
    private final PostsService postService;

    private final JwtService jwtService;
    private final JwtService jwtUtil;
    public PostsController(PostsService postService, JwtService jwtService, JwtService jwtUtil) {
        this.postService = postService;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }
    @GetMapping("/all")
    public ResponseEntity<CustomResponse<Object>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Object>> getPostById(@PathVariable int id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    @GetMapping("/user/all/{userId}")
    public ResponseEntity<CustomResponse<Object>>  getAllPostsByUser(@PathVariable int userId){
        return new ResponseEntity<>(postService.getAllPostsByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<CustomResponse<Object>>  getAllPostsByUserFollowing(@PathVariable int userId){
        return new ResponseEntity<>(postService.getPostsByUserFollowing(userId),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody PostRequest post){
        return new ResponseEntity<>(postService.create(post),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        return new ResponseEntity<>(postService.delete(Integer.parseInt(id)),HttpStatus.CREATED);
    }

}
