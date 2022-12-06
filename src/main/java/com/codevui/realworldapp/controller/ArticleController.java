package com.codevui.realworldapp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codevui.realworldapp.exception.custom.CustomNotFoundException;
import com.codevui.realworldapp.model.article.dto.ArticleDTOCreate;
import com.codevui.realworldapp.model.article.dto.ArticleDTOFilter;
import com.codevui.realworldapp.model.article.dto.ArticleDTOResponse;
import com.codevui.realworldapp.model.article.dto.ArticleDTOUpdate;
import com.codevui.realworldapp.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {
    
    private final ArticleService articleService;
    
    @PostMapping("")
    public Map<String, ArticleDTOResponse> createArticle(@RequestBody Map<String,ArticleDTOCreate> articleDTOCreateMap){
        return articleService.createArticle(articleDTOCreateMap);
    }

    @GetMapping("/{slug}")
    public Map<String, ArticleDTOResponse> getArticle(@PathVariable String slug) throws CustomNotFoundException{
        return articleService.getArticle(slug);
    }

    @GetMapping("")
    public Map<String, Object> getListAticles(
    @RequestParam(name = "tag", required = false) String tag,
    @RequestParam(name = "author", required = false) String author,
    @RequestParam(name = "favorited", required = false) String favorited,
    @RequestParam(name = "limit", defaultValue = "20") Integer limit,
    @RequestParam(name = "offset", defaultValue = "0") Integer offset){
        ArticleDTOFilter articleDTOFilter = ArticleDTOFilter
        .builder()
        .tag(tag)
        .author(author)
        .favorited(favorited)
        .limit(limit)
        .offset(offset)
        .build();
        return articleService.getListArticles(articleDTOFilter);
    }

    @PostMapping("/{slug}/favorited")
    public Map<String, ArticleDTOResponse> favoritedArticle(@PathVariable String slug) throws CustomNotFoundException{
        return articleService.favoritedArticle(slug);
    }

    @DeleteMapping("/{slug}/favorited")
    public Map<String,ArticleDTOResponse> unfavoritedArticle(@PathVariable String slug) throws CustomNotFoundException{
        return articleService.unfavoritedArticle(slug);
    }

    @PutMapping("/{slug}")
    public Map<String, ArticleDTOResponse> updateArticle(@PathVariable String slug, @RequestBody Map<String,ArticleDTOUpdate> aritcleDTOUpdate) throws CustomNotFoundException{
        return articleService.updateArticle(slug, aritcleDTOUpdate);

    }
}
