package com.ll.medium.domain.aticle.article.repository;

import com.ll.medium.domain.aticle.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByPublished(boolean b);
}
