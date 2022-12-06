package com.codevui.realworldapp.service;

import java.util.Map;

import com.codevui.realworldapp.model.comment.dto.CommentDTORequest;
import com.codevui.realworldapp.model.comment.dto.CommentDTOReponse;

public interface CommentService {

    public Map<String, CommentDTOReponse> addComment(String slug
    , Map<String, CommentDTORequest> commentDTORequest);

    public Map<String, Object> getCommentsFromArticle(String slug);

    
    
}
