package com.owwang.community.search.service;

import com.owwang.community.search.dao.ArticleDao;
import com.owwang.community.search.pojo.Article;
import entity.PageResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Classname ArticleService
 * @Description TODO
 * @Date 2020-01-10
 * @Created by WANG
 */
@Service
public class ArticleService{
    @Autowired
    private ArticleDao articleDao;


    public void save(Article article) {
        articleDao.save(article);
    }

    public Page<Article> findByKey(String key, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Article> pageDate = articleDao.findByContentLikeOrTitleOrId(key,key, key, pageRequest);
        return pageDate;
    }
}
