package com.codevui.realworldapp.model.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticleDTOFilter {
    
    private String tag;
    private String author;
    private String favorited;
    private int limit;
    private int offset;
    
}
