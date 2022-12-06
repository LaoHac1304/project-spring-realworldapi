package com.codevui.realworldapp.model.comment.dto;

import java.util.Date;

import com.codevui.realworldapp.model.article.dto.AuthorDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Builder
@Setter
@Getter
@AllArgsConstructor
public class CommentDTOReponse {

    private int id;
    private String body;
    private Date createAt;
    private Date updatedAt;
    private AuthorDTOResponse author;

    
}
