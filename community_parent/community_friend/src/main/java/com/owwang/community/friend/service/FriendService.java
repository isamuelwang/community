package com.owwang.community.friend.service;

import com.owwang.community.friend.dao.FriendDao;
import com.owwang.community.friend.dao.NoFriendDao;
import com.owwang.community.friend.pojo.Friend;
import com.owwang.community.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname FriendService
 * @Description TODO
 * @Date 2020-01-16
 * @Created by WANG
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userId, String frendId) {
        Friend friend = friendDao.findByUseridAndFriendid(userId, frendId);
        if(friend!=null){
            return 0;
        }
        //更新关系表
        Friend rFriend = new Friend();
        rFriend.setUserid(userId);
        rFriend.setFriendid(frendId);
        rFriend.setIslike("0");
        friendDao.save(rFriend);

        if(friendDao.findByUseridAndFriendid(frendId, userId)!=null){
            friendDao.updateIslike("1",userId,frendId);
            friendDao.updateIslike("1",frendId,userId);
        }
        return 1;
    }

    public int addNoFriend(String userId, String friendId) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if(noFriend!=null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriend(userid,friendid);
        friendDao.updateIslike("0",friendid,userid);
        NoFriend noFriend = new NoFriend();
        noFriend.setFriendid(friendid);
        noFriend.setUserid(userid);
        noFriendDao.save(noFriend);
    }
}
