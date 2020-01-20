package com.owwang.community.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 更新好友粉丝数量和增加用户关注数量
 * @return com.owwang.community.entity.Result
 * @Date 2020-01-16
 * @auther Samuel
 */
@FeignClient("community-user")
public interface UserClient {
    @PutMapping("/user/{userid}/{friendid}/{number}")
    public void updateFanscountAndFollowcount(@PathVariable String userid,
                                               @PathVariable String friendid,
                                               @PathVariable int number);
}
