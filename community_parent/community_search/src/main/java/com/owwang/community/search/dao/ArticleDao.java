package com.owwang.community.search.dao;

import com.owwang.community.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Classname ArticleDao
 * @Description TODO
 * @Date 2020-01-10
 * @Created by WANG
 */
public interface ArticleDao extends ElasticsearchRepository<Article, String> {

    Page<Article> findByContentLikeOrTitleOrId(String title, String content,String id , Pageable pageable);
}
