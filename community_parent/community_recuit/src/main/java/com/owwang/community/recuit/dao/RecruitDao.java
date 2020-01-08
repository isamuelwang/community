package com.owwang.community.recuit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.owwang.community.recuit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    //where state = ? order by createtime
    public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
