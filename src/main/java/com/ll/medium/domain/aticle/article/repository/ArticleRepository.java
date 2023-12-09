package com.ll.medium.domain.aticle.article.repository;

import com.ll.medium.domain.aticle.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}