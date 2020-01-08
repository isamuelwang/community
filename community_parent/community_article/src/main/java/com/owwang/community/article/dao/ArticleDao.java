package com.owwang.community.article.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.owwang.community.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    /**
     * 审核通过修改状态码
     * @param id
     * @return void
     * @Date 2020-01-08
     * @auther Samuel
     */
    @Modifying//增删改需要加这个注解
    @Query(value ="UPDATE tb_article SET state=1 WHERE id = ?",nativeQuery = true)
    public void updateState(String id);

    /**
     * 点赞加1
     * @param id
     * @return void
     * @Date 2020-01-08
     * @auther Samuel
     */
    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup=thumbup+1 WHERE id = 1",nativeQuery = true)
    public void addThumbup(String id);
}
