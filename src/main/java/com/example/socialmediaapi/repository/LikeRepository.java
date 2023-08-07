package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    void deleteLikeById(int id);
    List<Like> findAllByPost_Id(int postId);
    List<Like> findAllByUser_Id(int userId);
    Optional<Like> findByUser_IdAndPost_Id(int userId, int postId);
}
