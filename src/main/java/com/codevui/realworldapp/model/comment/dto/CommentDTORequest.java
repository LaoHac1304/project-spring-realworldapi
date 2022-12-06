package com.codevui.realworldapp.model.comment.dto;

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
public class CommentDTORequest {

    private String body;
}
