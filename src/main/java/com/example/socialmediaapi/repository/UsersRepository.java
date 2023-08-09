package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    void deleteById(int id);
    Optional<User> findByEmail(String email);
}
