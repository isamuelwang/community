package com.owwang.community.recuit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.owwang.community.recuit.pojo.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{
	/**
	 * 查询热门信息
	 * @param ishot
	 * @return java.util.List<com.owwang.community.recuit.pojo.Enterprise>
	 * @Date 2020-01-07
	 * @auther Samuel
	 */
	public List<Enterprise> findByIshot(String ishot);//where ishot = ?
}
