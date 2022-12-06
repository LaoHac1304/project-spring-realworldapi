package com.codevui.realworldapp.model.article.dto;

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
public class AuthorDTOResponse {
    
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
