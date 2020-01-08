package com.owwang.community.qa.dao;

import com.sun.javafx.logging.PulseLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.owwang.community.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query(value = "SELECT * FROM tb_problem LEFT JOIN tb_pl ON problemid=id WHERE labelid = ? ORDER BY replytime DESC",nativeQuery = true)
    public Page<Problem> newlist(Integer labelid, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem LEFT JOIN tb_pl ON problemid=id WHERE labelid = ? ORDER BY reply DESC",nativeQuery = true)
    public Page<Problem> hotlist(Integer labelid, Pageable pageable);

    /**
     * 没有回复问题分页列表
     * @param labelid
     * @return org.springframework.data.domain.Page<com.owwang.community.qa.pojo.Problem>
     * @Date 2020-01-08
     * @auther Samuel
     */
    @Query(value = "SELECT * FROM tb_problem LEFT JOIN tb_pl ON problemid=id WHERE labelid = ? AND reply=0 ORDER BY createtime DESC",nativeQuery = true)
    public Page<Problem> waitlist(Integer labelid, Pageable pageable);
}
