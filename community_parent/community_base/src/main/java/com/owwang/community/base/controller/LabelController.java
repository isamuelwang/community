package com.owwang.community.base.controller;

import com.owwang.community.base.pojo.Label;
import com.owwang.community.base.service.LabelService;
import com.owwang.community.entity.PageResult;
import com.owwang.community.entity.Result;
import com.owwang.community.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname BaseController
 * @Description TODO
 * @Date 2020-01-06
 * @Created by WANG
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
@RefreshScope
public class LabelController {
    @Autowired
    private LabelService labelService;
    @Autowired
    private HttpServletRequest request;
    @Value("${test_value}")
    private String testValue;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        System.out.println(testValue);
        String header = request.getHeader("Authorization");
        System.out.println("===========header:"+header);
        return new Result(true, StatusCode.OK, "查询成功",
                labelService.findAll());
    }

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    public Result findById(@PathVariable("label") String labelId) {
        return new Result(true, StatusCode.OK, "查询成功",
                labelService.findById(labelId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId, @RequestBody Label label) {
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label) {
        List<Label> list = labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value="/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,@PathVariable Integer page,@PathVariable Integer size){
        Page<Label> pageData = labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",
                new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }

}
