package com.owwang.community.spit.controller;
import com.owwang.community.spit.pojo.Spit;
import com.owwang.community.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname SpitController
 * @Description TODO
 * @Date 2020-01-09
 * @Created by WANG
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId){
        Result result = spitService.thumbup(spitId);
        return result;
    }

    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentid,
                                 @PathVariable int page,@PathVariable int size){
        Page<Spit> pageResult = spitService.findByParentId(parentid, page, size);
        PageResult<Spit> spitPageResult = new PageResult<>(pageResult.getTotalElements(),pageResult.getContent());
        return new Result(true,StatusCode.OK,"查询成功",spitPageResult);
    }

    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    @GetMapping(value = "/{spitId}")
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    @PostMapping()
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    @PutMapping(value = "/{spitId}")
    public Result save(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping("/{spitId}")
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
