package com.codevui.realworldapp.model.comment.mapper;

import java.util.Date;

import com.codevui.realworldapp.entity.Comment;
import com.codevui.realworldapp.model.article.mapper.ArticleMapper;
import com.codevui.realworldapp.model.comment.dto.CommentDTOReponse;
import com.codevui.realworldapp.model.comment.dto.CommentDTORequest;

public class CommentMapper {

    public static Comment toComment(CommentDTORequest commentDTOCreate) {

        Date now = new Date();
       return Comment
       .builder()
       .body(commentDTOCreate.getBody())
       .createdAt(now)
       .updatedAt(now)
       .build();
    }

    public static CommentDTOReponse toCommentDTOReponse(Comment comment, boolean isFollowing) {

        return CommentDTOReponse
        .builder()
        .id(comment.getId())
        .body(comment.getBody())
        .createAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .author(ArticleMapper.toAuthorDTOResponse(comment.getAuthor(), isFollowing))
        .build();
    }
    
}
