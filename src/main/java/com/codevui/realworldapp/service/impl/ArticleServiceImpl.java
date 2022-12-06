package com.codevui.realworldapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codevui.realworldapp.entity.Article;
import com.codevui.realworldapp.entity.User;
import com.codevui.realworldapp.exception.custom.CustomNotFoundException;
import com.codevui.realworldapp.model.article.dto.ArticleDTOCreate;
import com.codevui.realworldapp.model.article.dto.ArticleDTOFilter;
import com.codevui.realworldapp.model.article.dto.ArticleDTOResponse;
import com.codevui.realworldapp.model.article.dto.ArticleDTOUpdate;
import com.codevui.realworldapp.model.article.mapper.ArticleMapper;
import com.codevui.realworldapp.model.user.CustomError;
import com.codevui.realworldapp.repository.ArticleRepository;
import com.codevui.realworldapp.repository.UserRepository;
import com.codevui.realworldapp.repository.custom.ArticleCriteria;
import com.codevui.realworldapp.service.ArticleService;
import com.codevui.realworldapp.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ArticleCriteria articleCriteria;
    private final UserRepository userRepository;

    @Override
    public Map<String, ArticleDTOResponse> createArticle(Map<String, ArticleDTOCreate> articleDTOCreateMap) {
        ArticleDTOCreate articleDTOCreate = articleDTOCreateMap.get("article");

        Article article = ArticleMapper.toArticle(articleDTOCreate);
        User currentUser = userService.getUserLoggedIn();

        article.setAuthor(currentUser);

        article = articleRepository.save(article);

        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, false, 0, false);
        wrapper.put("article", articleDTOResponse);
        return wrapper;
    }

    @Override
    public Map<String, ArticleDTOResponse> getArticle(String slug) throws CustomNotFoundException {
        Optional<Article> articleOptional = articleRepository.findBySlug(slug);
        if (!articleOptional.isPresent()) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("Article was not found").build());
        }

        Article article = articleOptional.get();

        // check favorited
        User userLoggedIn = userService.getUserLoggedIn();
        boolean favorited = false;
        favorited = checkFavorited(userLoggedIn, article);

        //count the number of favorite of article
        int favoritesCount = 0;
        favoritesCount = countTheNumberOfFavoritedArticle(article);

        // check isFollowing
        boolean isFollowing = false;
        User author = article.getAuthor();
        Set<User> followerOfAuthor = author.getFollowers();
        for (User user : followerOfAuthor) {
            if (user.getId() == userLoggedIn.getId()) {
                isFollowing = true;
                break;
            }
        }

        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, favorited, favoritesCount,
                isFollowing);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);
        return wrapper;
    }

    @Override
    public Map<String, Object> getListArticles(ArticleDTOFilter articleDTOFilter) {

        Map<String, Object> result = articleCriteria.findAll(articleDTOFilter);

        List<Article> listArticles = (List<Article>) result.get("listArticles");
        Long totalArticle = (Long) result.get("totalArticle");

        List<ArticleDTOResponse> listArticleDTOResponses = listArticles.stream()
                .map(article -> ArticleMapper.toArticleDTOResponse(article, false, 0, false))
                .collect(Collectors.toList());

        // map(T -> A) chuyen tu kieu T sang kieu A

        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("articles", listArticleDTOResponses);
        wrapper.put("articlesCount", totalArticle);

        return wrapper;
    }

    @Override
    public Map<String, ArticleDTOResponse> favoritedArticle(String slug) throws CustomNotFoundException {

        Optional<Article> articleOptional = articleRepository.findBySlug(slug);
        if (!articleOptional.isPresent()) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("Article is not found").build());
        }
        User userLogined = userService.getUserLoggedIn();

        Article article = articleOptional.get();

        // check current user co like article ?
        boolean isFavorited = false;
        isFavorited = checkFavorited(userLogined, article);

        if (!isFavorited) {
            isFavorited = true;
            article.getUserFavorited().add(userLogined);
            articleRepository.save(article);
            isFavorited = true;
            userLogined.getArticleFavorited().add(article);
            userRepository.save(userLogined);
        }

        //count the number of favorite of article
        int favoritesCount = 0;
        favoritesCount = countTheNumberOfFavoritedArticle(article);

        // check isFollowing
        boolean isFollowing = false;

        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, isFavorited, favoritesCount,
                isFollowing);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);

        return wrapper;
    }

    @Override
    public Map<String, ArticleDTOResponse> unfavoritedArticle(String slug) throws CustomNotFoundException {
        Optional<Article> articleOptional = articleRepository.findBySlug(slug);

        if (!articleOptional.isPresent()) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("Article is not found").build());
        }

        // get user is loggin
        User userLoggined = userService.getUserLoggedIn();

        // lay article
        Article article = articleOptional.get();

        // check current user co like article ?
        boolean isFavorited = false;
        isFavorited = checkFavorited(userLoggined, article);

        if (isFavorited) {
            isFavorited = false;
            userLoggined.getArticleFavorited().remove(article);
            userRepository.save(userLoggined);
        }
        //count the number of favorite of article
        int favoritesCount = 0;
        favoritesCount = countTheNumberOfFavoritedArticle(article);

        // check isFollowing
        boolean isFollowing = false;


        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, isFavorited, favoritesCount,
                isFollowing);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);

        return wrapper;
    }

    private boolean checkFavorited(User user, Article article) {
        boolean isFavorited = false;
        Set<User> userFavoritedThisArticle = article.getUserFavorited();
        for (User userX : userFavoritedThisArticle) {
            if (user.getId() == userX.getId()) {
                isFavorited = true;
            }
        }
        return isFavorited;
    }

    private int countTheNumberOfFavoritedArticle(Article article){
        int count = 0;
        count = article.getUserFavorited().size();
        return count;
    }

    @Override
    public Map<String, ArticleDTOResponse> updateArticle(String slug, Map<String, ArticleDTOUpdate> aritcleDTOUpdate) throws CustomNotFoundException {

        Optional<Article> aOptional = articleRepository.findBySlug(slug);
        if (!aOptional.isPresent()){
            throw new CustomNotFoundException(CustomError.builder().code("404").message("The slug is not exist").build());
        }

        ArticleDTOUpdate articleUpdate = aritcleDTOUpdate.get("article"); 
        
        Article article = aOptional.get();

        article = ArticleMapper.toArticle(articleUpdate, article);

        article = articleRepository.save(article);

        boolean isFollowing = false;
        User author = article.getAuthor();
        Set<User> followerOfAuthor = author.getFollowers();
        User userLoggedIn = userService.getUserLoggedIn();
        for (User user : followerOfAuthor) {
            if (user.getId() == userLoggedIn.getId()) {
                isFollowing = true;
                break;
            }
        }

        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();

        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, isFollowing);

        wrapper.put("article", articleDTOResponse);

        return wrapper;
    }

    @Override
    public int getArticle(String slug, boolean isGetId) {
        Optional<Article> articleOptional = articleRepository.findBySlug(slug);
        Article article = articleOptional.get();
        return article.getId();
    }

}
