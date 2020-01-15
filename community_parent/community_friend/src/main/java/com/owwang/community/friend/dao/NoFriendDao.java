package com.owwang.community.friend.dao;

import com.owwang.community.friend.pojo.Friend;
import com.owwang.community.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname NoFriendDao
 * @Description TODO
 * @Date 2020-01-16
 * @Created by WANG
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {
    NoFriend findByUseridAndFriendid(String userid,String friendid);
}
