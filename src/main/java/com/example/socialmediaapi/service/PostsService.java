package com.example.socialmediaapi.service;

import com.example.socialmediaapi.entity.Follow;
import com.example.socialmediaapi.entity.Post;
import com.example.socialmediaapi.entity.User;
import com.example.socialmediaapi.repository.PostsRepository;
import com.example.socialmediaapi.request.PostRequest;
import com.example.socialmediaapi.response.CustomResponse;
import com.example.socialmediaapi.response.PostResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserService userService;

    public PostsService(PostsRepository postsRepository, UserService userService) {
        this.postsRepository = postsRepository;
        this.userService = userService;
    }

    public CustomResponse<Object> delete(int id) {
        try {
            postsRepository.deleteById(id);
            return new CustomResponse<>(true, false, "post deletion successful", HttpStatus.CREATED.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "deletion failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public CustomResponse<Object> getAllPosts() {
        List<Post> posts = postsRepository.findAll();
        if (posts.size()==0) {
            return new CustomResponse<>(false, true, "No posts found", HttpStatus.OK.value());
        }
        return new CustomResponse<>(true, false, posts, HttpStatus.OK.value());
    }

    public CustomResponse<Object> getPostById(int id) {
        var post = postsRepository.findById(id);
        return new CustomResponse<>(true, false, post, HttpStatus.OK.value());
    }

    public CustomResponse<Object> create(PostRequest post) {
        Post dbPost = new Post();
        User user = new User();
        user.setId(post.getUserId());
        dbPost.setDescription(post.getDescription());
        dbPost.setUser(user);
        postsRepository.save(dbPost);
        PostResponse postResponse = new PostResponse();
        postResponse.setDescription(dbPost.getDescription());
        postResponse.setId(dbPost.getId());
        postResponse.setUserId(user.getId());
        return new CustomResponse<>(true, false, postResponse, HttpStatus.CREATED.value());
    }

    public CustomResponse<Object> getAllPostsByUser(int userId) {
        List<Post> posts = new ArrayList<>();
        posts = postsRepository.findAllByUser_IdOrderByIdDesc(userId);
        return new CustomResponse<>(false, true, posts, HttpStatus.OK.value());
    }

    public CustomResponse<Object> getPostsByUserFollowing(int userId) {

        var follows = userService.getUserFollowing(userId);
        List<Post> set = new ArrayList<>();

        for(Follow user : follows){
            set.addAll(postsRepository.findAllByUser_IdOrderByIdDesc(user.getId()));
        }

        set.sort(Comparator.comparing(Post::getId).reversed());

        if (set.size()==0) {
            return new CustomResponse<>(false, true, "No posts found", HttpStatus.OK.value());
        }
        return new CustomResponse<>(true, false, set, HttpStatus.OK.value());
    }

}
