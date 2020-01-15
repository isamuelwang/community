package com.owwang.community.friend.controller;

import com.owwang.community.friend.client.UserClient;
import com.owwang.community.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname FridenController
 * @Description TODO
 * @Date 2020-01-16
 * @Created by WANG
 */
@RestController
@RequestMapping("/friend")
public class FridenController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserClient userClient;

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){
        Claims claims = (Claims)request.getAttribute("claims_user");
        if(claims==null){
            return new Result(false,StatusCode.ERROR,"权限不足");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid,friendid);
        userClient.updateFanscountAndFollowcount(userid,friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    @PutMapping("/like/{friendId}/{type}")
    public Result addFriend(@PathVariable String friendId, @PathVariable String type) {
        //验证是否登录，并拿到用户Id
        Claims claims = null;
        try {
            claims = (Claims) request.getAttribute("claims_user");
        } catch (Exception e) {
            return new Result(false, StatusCode.ERROR, "权限不足");
        }
        if (claims == null) {
            //当前用户没有user角色
            return new Result(false, StatusCode.ERROR, "权限不足");
        }
        //获取userId
        String userId = claims.getId();
        //判断是添加好友还是添加非好友
        if (type != null) {
            if (type.equals("1")) {
                //添加好友
                int flag = friendService.addFriend(userId, friendId);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                } else if (flag == 1) {
                    userClient.updateFanscountAndFollowcount(userId,friendId,1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            } else if (type.equals("2")) {
                //添加非好友
                int flag = friendService.addNoFriend(userId,friendId);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                } else if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
        return new Result(false, StatusCode.ERROR, "参数异常");
    }
}
