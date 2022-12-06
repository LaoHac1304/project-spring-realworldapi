package com.codevui.realworldapp.model.article.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTOResponse {

    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private Date createdAt;
    private Date updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private AuthorDTOResponse author;
}
