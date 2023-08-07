package com.example.socialmediaapi.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(nullable = false, name = "name", unique = false)
    private String name;

    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "password")
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    Set<Follow> following;
    @OneToMany(mappedBy = "following",cascade = CascadeType.ALL)
    Set<Follow> followers;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    Set<Post> posts;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    Set<Like> likes;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    Set<UserImage> images;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    Set<Comment>comments;

}
