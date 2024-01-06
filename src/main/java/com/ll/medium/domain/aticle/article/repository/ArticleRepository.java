package com.ll.medium.domain.aticle.article.repository;

import com.ll.medium.domain.aticle.article.entity.Article;
import com.ll.medium.domain.member.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByPublished(boolean b, Pageable pageable);
    Page<Article> findByAuthor(Member author, Pageable pageable);

    ArrayList<Article> findTop30ByPublishedTrueOrderByCreateDateDesc();
}
