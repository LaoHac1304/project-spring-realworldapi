package com.codevui.realworldapp.repository.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.codevui.realworldapp.entity.Comment;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentCriteria{

    private final EntityManager em;

    public Map<String, Object> findAll(int article_id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();// tao ra doi tuong chua cau lenh truy van
        CriteriaQuery<Comment> q = cb.createQuery(Comment.class);
        Root<Comment> root = q.from(Comment.class);
        q.select(root).where(cb.equal(root.get("article"), article_id));

        TypedQuery<Comment> query = em.createQuery(q);
        List<Comment> listComment = query.getResultList();

        Map<String, Object> result = new HashMap<>();

        result.put("comments", listComment);


        return result;
    }

    
    
}
