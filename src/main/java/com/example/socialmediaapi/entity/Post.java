package com.example.socialmediaapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "posts")
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "description")
    private String description;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    Set<Like> likes;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    Set<PostImage> postImages;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    Set<Comment> comments;
}