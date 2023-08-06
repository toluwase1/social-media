package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Integer> {
    void deleteById(int id);
    User findByEmail(String email);
}
