package com.codevui.realworldapp.service;

import java.util.Map;

import com.codevui.realworldapp.exception.custom.CustomNotFoundException;
import com.codevui.realworldapp.model.article.dto.ArticleDTOCreate;
import com.codevui.realworldapp.model.article.dto.ArticleDTOFilter;
import com.codevui.realworldapp.model.article.dto.ArticleDTOResponse;
import com.codevui.realworldapp.model.article.dto.ArticleDTOUpdate;

public interface ArticleService {

    public Map<String, ArticleDTOResponse> createArticle(Map<String, ArticleDTOCreate> articleDTOCreateMap);

    public Map<String, ArticleDTOResponse> getArticle(String slug) throws CustomNotFoundException;

    public Map<String, Object> getListArticles(ArticleDTOFilter articleDTOFilter);

    public Map<String, ArticleDTOResponse> favoritedArticle(String slug) throws CustomNotFoundException;

    public Map<String, ArticleDTOResponse> unfavoritedArticle(String slug) throws CustomNotFoundException;

    public Map<String, ArticleDTOResponse> updateArticle(String slug, Map<String, ArticleDTOUpdate> aritcleDTOUpdate) throws CustomNotFoundException;
    
    public int getArticle(String slug, boolean isGetId);
}
