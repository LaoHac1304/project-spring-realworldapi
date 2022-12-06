package com.codevui.realworldapp.model.article.mapper;

import java.util.Date;

import com.codevui.realworldapp.entity.Article;
import com.codevui.realworldapp.entity.User;
import com.codevui.realworldapp.model.article.dto.ArticleDTOCreate;
import com.codevui.realworldapp.model.article.dto.ArticleDTOResponse;
import com.codevui.realworldapp.model.article.dto.ArticleDTOUpdate;
import com.codevui.realworldapp.model.article.dto.AuthorDTOResponse;
import com.codevui.realworldapp.util.SlugUtil;

public class ArticleMapper {

    public static Article toArticle(ArticleDTOCreate articleDTOCreate) {
        
        Date now = new Date();
        Article article = Article.builder()
        .slug(SlugUtil.getSlug(articleDTOCreate.getTitle()))
        .title(articleDTOCreate.getTitle())
        .description(articleDTOCreate.getDescription())
        .body(articleDTOCreate.getBody())
        .createdAt(now)
        .updatedAt(now)
        .build();
        article.setTagList(articleDTOCreate.getTagList());
        return article;
    }

    public static ArticleDTOResponse toArticleDTOResponse(
        Article article, boolean favorited
        , int favoritesCount, boolean isFollowing) {
        
        
        return ArticleDTOResponse.builder()
        .slug(article.getSlug())
        .title(article.getTitle())
        .description(article.getDescription())
        .body(article.getBody())
        .tagList(article.getTagList())
        .createdAt(article.getCreatedAt())
        .updatedAt(article.getUpdatedAt())
        .favorited(favorited)
        .favoritesCount(favoritesCount)
        .author(toAuthorDTOResponse(article.getAuthor(), isFollowing))
        .build();
    }

    public static AuthorDTOResponse toAuthorDTOResponse(User author, boolean isFollowing) {
        return AuthorDTOResponse
        .builder()
        .username(author.getUsername())
        .bio(author.getBio())
        .image(author.getImage())
        .following(isFollowing)
        .build();
    }

    public static Article toArticle(ArticleDTOUpdate articleUpdate, Article article) {

        if (articleUpdate.getTitle() != null){
            article.setTitle(articleUpdate.getTitle());
            article.setSlug(SlugUtil.getSlug(articleUpdate.getTitle()));
        }

        if (articleUpdate.getDescription() != null){
            article.setDescription(articleUpdate.getDescription());
        }

        if (articleUpdate.getBody() != null){
            article.setBody(articleUpdate.getBody());
        }

        return article;
    }

    public static ArticleDTOResponse toArticleDTOResponse(Article article, boolean isFollowing) {
        return ArticleDTOResponse.builder()
        .slug(article.getSlug())
        .title(article.getTitle())
        .description(article.getDescription())
        .body(article.getBody())
        .tagList(article.getTagList())
        .createdAt(article.getCreatedAt())
        .updatedAt(article.getUpdatedAt())
        .favorited(article.isFavorited())
        .favoritesCount(article.getFavoritesCount())
        .author(toAuthorDTOResponse(article.getAuthor(), isFollowing))
        .build();
    }
    
}
