package com.codevui.realworldapp.model.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTOUpdate {

    String title;
    String description;
    String body;
    String slug;
    
}
