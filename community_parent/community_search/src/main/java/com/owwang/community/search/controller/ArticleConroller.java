package com.owwang.community.search.controller;

import com.owwang.community.search.pojo.Article;
import com.owwang.community.search.service.ArticleService;
import com.owwang.community.entity.PageResult;
import com.owwang.community.entity.Result;
import com.owwang.community.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname ArticleConroller
 * @Description TODO
 * @Date 2020-01-10
 * @Created by WANG
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleConroller {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "索引创建成功");
    }

    @GetMapping("/search")
    public Result findByKey(@RequestBody Map map) {
        Page<Article> pageData = articleService.findByKey((String) map.get("key"), (int) map.get("page"), (int) map.get("size"));
        List<Article> articles = new ArrayList<>();
        Long total = 0L;
        for (Article article : pageData.getContent()) {
            if (article.getState() != null) {
                if (article.getState().equals("1")) {
                    articles.add(article);
                    total++;
                }
            }
        }
        PageResult pageResult = new PageResult<Article>(total, articles);
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }
}
