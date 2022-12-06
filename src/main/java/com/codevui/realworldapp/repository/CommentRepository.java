package com.codevui.realworldapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codevui.realworldapp.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
}
