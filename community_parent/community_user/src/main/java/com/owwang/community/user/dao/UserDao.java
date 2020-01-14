package com.owwang.community.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.owwang.community.user.pojo.User;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    Boolean existsByMobile(String mobile);
    User findByMobile(String mobile);
}
