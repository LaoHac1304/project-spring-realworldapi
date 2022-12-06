package com.codevui.realworldapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codevui.realworldapp.entity.Comment;
import com.codevui.realworldapp.entity.User;
import com.codevui.realworldapp.model.comment.dto.CommentDTOReponse;
import com.codevui.realworldapp.model.comment.dto.CommentDTORequest;
import com.codevui.realworldapp.model.comment.mapper.CommentMapper;
import com.codevui.realworldapp.repository.CommentRepository;
import com.codevui.realworldapp.repository.custom.CommentCriteria;
import com.codevui.realworldapp.service.ArticleService;
import com.codevui.realworldapp.service.CommentService;
import com.codevui.realworldapp.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository; 

    private final UserService userService;

    private final ArticleService articleService;

    private final CommentCriteria commentCriteria;
    
    @Override
    public Map<String, CommentDTOReponse> addComment(String slug,
            Map<String, CommentDTORequest> commentDTORequest) {

        User currentUser = userService.getUserLoggedIn();
        
        CommentDTORequest commentDTOCreate = commentDTORequest.get("comment");
        Comment comment = CommentMapper.toComment(commentDTOCreate);
        comment.setAuthor(currentUser);

        comment = commentRepository.save(comment);

        CommentDTOReponse commentDTOReponse = CommentMapper.toCommentDTOReponse(comment, false);

        Map<String, CommentDTOReponse> wrapper = new HashMap<>();

        wrapper.put("comment", commentDTOReponse);

        return wrapper;
    }

    @Override
    public Map<String, Object> getCommentsFromArticle(String slug) {

        int article_id = articleService.getArticle(slug, true);
        System.out.println(article_id);

        Map<String, Object> result = commentCriteria.findAll(article_id);

        List<Comment> listComments = (List<Comment>) result.get("comments");

        List<CommentDTOReponse> listCommentDTOReponses = listComments.stream()
        .map(comment -> CommentMapper.toCommentDTOReponse(comment, false))
        .collect(Collectors.toList());

        Map<String, Object> wrapper = new HashMap<>();

        wrapper.put("comments", listCommentDTOReponses);

        
        return wrapper;
    }
}
