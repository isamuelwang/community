package com.owwang.community.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.owwang.community.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    Boolean existsByMobile(String mobile);

    User findByMobile(String mobile);

    @Modifying
    @Query(value = "update tb_user set fanscount=fanscount+? where id=?",nativeQuery = true)
    void updateFanscount(int number, String friendid);

    @Modifying
    @Query(value = "update tb_user set followcount=followcount+? where id=?",nativeQuery = true)
    void updateFollowcount(int number, String userid);
}
