package com.owwang.community.qa.controller;
import java.util.Map;

import com.owwang.community.qa.client.BaseClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.owwang.community.qa.pojo.Problem;
import com.owwang.community.qa.service.ProblemService;

import com.owwang.community.entity.PageResult;
import com.owwang.community.entity.Result;
import com.owwang.community.entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private BaseClient baseClient;

	@GetMapping("/label/{labelId}")
	public Result findByLabelId(@PathVariable String labelId){
		Result result = baseClient.findById(labelId);
		return result;
	}

	@RequestMapping(value = "/waitlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result waitlist(@PathVariable Integer label,@PathVariable Integer page,@PathVariable Integer size){
		Page<Problem> pageData = problemService.waitlist(label, page, size);
		PageResult<Problem> problemPageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
		return new Result(true,StatusCode.OK,"查询成功",problemPageResult);
	}

	@RequestMapping(value = "/hotlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result hotlist(@PathVariable Integer label,@PathVariable Integer page,@PathVariable Integer size){
		Page<Problem> pageData = problemService.hotlist(label, page, size);
		PageResult<Problem> problemPageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
		return new Result(true,StatusCode.OK,"查询成功",problemPageResult);
	}

	@RequestMapping(value = "/newlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result newlist(@PathVariable Integer label,@PathVariable Integer page,@PathVariable Integer size){
		Page<Problem> pageData = problemService.newlist(label, page, size);
		PageResult<Problem> problemPageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
		return new Result(true,StatusCode.OK,"查询成功",problemPageResult);
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		String token = (String)request.getAttribute("claims_user");
		if(StringUtils.isEmpty(token)){
			return new Result(false,StatusCode.ERROR,"权限不足");
		}
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
