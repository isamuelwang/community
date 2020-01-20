package com.owwang.community.qa.client;

import com.owwang.community.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname BaseClient
 * @Description TODO
 * @Date 2020-01-15
 * @Created by WANG
 */
@FeignClient("community-base")
public interface BaseClient {
    @RequestMapping(value = "/label/{label}", method = RequestMethod.GET)
    Result findById(@PathVariable("label") String labelId);
}
