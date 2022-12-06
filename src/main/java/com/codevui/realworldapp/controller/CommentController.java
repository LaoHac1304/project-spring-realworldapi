package com.codevui.realworldapp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codevui.realworldapp.model.comment.dto.CommentDTOReponse;
import com.codevui.realworldapp.model.comment.dto.CommentDTORequest;
import com.codevui.realworldapp.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{slug}/comments")
    public Map<String, CommentDTOReponse> addComment(
        @PathVariable String slug
        , @RequestBody Map<String, CommentDTORequest> commentDTORequest){
            return commentService.addComment(slug, commentDTORequest);
            
        }
        
    @GetMapping("/{slug}/comments")
    public Map<String, Object> getCommentsFromArticle(@PathVariable String slug){

        return commentService.getCommentsFromArticle(slug);
    }
    
}
