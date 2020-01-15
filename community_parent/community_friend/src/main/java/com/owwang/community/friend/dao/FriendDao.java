package com.owwang.community.friend.dao;

import com.owwang.community.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Classname FriendDao
 * @Description TODO
 * @Date 2020-01-16
 * @Created by WANG
 */
public interface FriendDao extends JpaRepository<Friend,String> {
    Friend findByUseridAndFriendid(String userid,String friendid);

    @Modifying
    @Query(value = "UPDATE tb_friend SET islike=? where userid=? and friendid=?",nativeQuery = true)
    void updateIslike(String islike,String userid,String friendid);

    @Modifying
    @Query(value = "delete from tb_friend where userid=? and friendid=?",nativeQuery = true)
    void deleteFriend(String userid, String friendid);
}
