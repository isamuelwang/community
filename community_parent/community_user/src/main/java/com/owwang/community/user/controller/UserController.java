package com.owwang.community.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.owwang.community.user.pojo.User;
import com.owwang.community.user.service.UserService;

import com.owwang.community.entity.PageResult;
import com.owwang.community.entity.Result;
import com.owwang.community.entity.StatusCode;
import com.owwang.community.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;

    /**
     * @Description TODO 更新好友粉丝数量和增加用户关注数量
     * @return com.owwang.community.entity.Result
     * @Date 2020-01-16
     * @auther Samuel
     */
    @PutMapping("/{userid}/{friendid}/{number}")
    private void updateFanscountAndFollowcount(@PathVariable String userid,
                                               @PathVariable String friendid,
                                               @PathVariable int number){
        userService.updateFanscountAndFollowcount(number,userid,friendid);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User result = userService.login(user);
        if (result == null) {
            return new Result(false, StatusCode.ERROR, "登录失败");
        }
        //与前端进行通信
        String token = jwtUtil.createJWT(result.getId(), result.getMobile(), "user");
        Map map = new HashMap();
        map.put("token", token);
        map.put("roles", "user");
        return new Result(true, StatusCode.OK, "登录成功", map);


    }

    @PostMapping("/register/{code}")
    public Result regist(@PathVariable String code, @RequestBody User user) {
        Result result = userService.add(user, code);
        return result;
    }

    /**
     * 发送手机验证码
     *
     * @Date 2020-01-12
     * @auther Samuel
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        Result result = userService.sendSms(mobile);
        return result;
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }


    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
