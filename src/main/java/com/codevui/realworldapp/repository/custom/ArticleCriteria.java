package com.codevui.realworldapp.repository.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.codevui.realworldapp.entity.Article;
import com.codevui.realworldapp.model.article.dto.ArticleDTOFilter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ArticleCriteria {

    private final EntityManager em;

    public Map<String,Object> findAll(ArticleDTOFilter articleDTOFilter) {
        StringBuilder query = new StringBuilder(
                "select a from Article a left join a.author au left join a.userFavorited ufa where 1=1");
        Map<String, Object> params = new HashMap<>();

        if (articleDTOFilter.getTag() != null) {
            query.append(" and a.taglist like :tag");// :tag la truyen param vao
            params.put("tag", "%" + articleDTOFilter.getTag() + "%");
        }

        if (articleDTOFilter.getAuthor() != null) {
            query.append(" and au.username = :author");
            params.put("author", articleDTOFilter.getAuthor());
        }

        if (articleDTOFilter.getFavorited() != null) {
            query.append(" and ufa.username = :favorited");
            params.put("favorited", articleDTOFilter.getFavorited());
        }

        String countQuery = query.toString().replace("select a", "select count(a.id)");

        TypedQuery<Article> tQuery = em.createQuery(query.toString(), Article.class);

        Query tCountQuery = em.createQuery(countQuery);

        params.forEach((k, v) -> {
            tQuery.setParameter(k, v);
            tCountQuery.setParameter(k, v);
        });

        tQuery.setFirstResult(articleDTOFilter.getOffset());// bat dau page tai day
        tQuery.setMaxResults(articleDTOFilter.getLimit());// limit la bao nhieu
        Long totalArticle = (Long) tCountQuery.getSingleResult();
        List<Article> listArticles = tQuery.getResultList();

        Map<String,Object> results = new HashMap<>();
        results.put("listArticles", listArticles);
        results.put("totalArticle", totalArticle);
        return results;
    }
}
