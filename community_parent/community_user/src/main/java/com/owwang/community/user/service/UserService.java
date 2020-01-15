package com.owwang.community.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import entity.Result;
import entity.StatusCode;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.DatetimeUtil;
import util.IdWorker;

import com.owwang.community.user.dao.UserDao;
import com.owwang.community.user.pojo.User;
import util.JwtUtil;
import util.RegexUtils;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest httpServletRequest;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public Result add(User user,String code) {
		//手机验证码检查
		//String checkcodInRedis = "";
		String checkcodInRedis = (String)redisTemplate.opsForValue()
				.get("sms_checkcode:"+user.getMobile()+":code");
		//验证手机号是否正确
		if(!RegexUtils.checkMobile(user.getMobile())){
			return new Result(false,StatusCode.ERROR,"请输入正确的手机号");
		}
		if(userDao.existsByMobile(user.getMobile())){
			return new Result(false,StatusCode.ERROR,"该手机号已注册");
		}
		if(StringUtils.isBlank(checkcodInRedis)){
			return new Result(false,StatusCode.ERROR,"请先获取验证码");
		}
		if(!checkcodInRedis.equals(code)){
			return new Result(false,StatusCode.ERROR,"验证码错误");
		}
		user.setId( idWorker.nextId()+"" );
		user.setFollowcount(0);//关注数
		user.setFanscount(0);//粉丝数
		user.setOnline(0L);//在线时长
		user.setRegdate(new Date());//注册日期
		user.setUpdatedate(new Date());//更新日期
		user.setLastdate(new Date());//最后登陆日期
		//密码加密
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.save(user);
		redisTemplate.delete("sms_checkcode:"+user.getMobile()+":code");
		return new Result(true,StatusCode.OK,"注册成功");
	}

	/**
	 * 发送验证码
	 * @param mobile
	 * @return void
	 * @Date 2020-01-12
	 * @auther Samuel
	 */
	public Result sendSms(String mobile) {
		if(userDao.existsByMobile(mobile)){
			return new Result(false, StatusCode.ERROR,"手机号已注册");
		}
		//60秒不能重复发送
		String timecount = (String)redisTemplate.opsForValue().get("sms_checkcode:"+mobile+":conttime");
		if(!StringUtils.isBlank(timecount)){
			return new Result(false, StatusCode.ERROR,"发送频繁，请稍后再试");
		}
		//一天不能发超过20条
		String sCount = (String) redisTemplate.opsForValue().get("sms_checkcode:"+mobile+":count");
		int count=0;
		if(sCount!=null){
			count = Integer.parseInt(sCount);
		}
		if(count>=20){
			return new Result(false, StatusCode.ERROR,"短信发送过多");
		}
		//验证手机号是否正确
		if(!RegexUtils.checkMobile(mobile)){
			return new Result(false, StatusCode.ERROR,"请输入正确的手机号");
		}
		//生成四位随机数数字
		String random = RandomStringUtils.randomNumeric(5);
		//向缓存中中存
		redisTemplate.opsForValue().set("sms_checkcode:"+mobile+":code",random,30, TimeUnit.MINUTES);
		redisTemplate.opsForValue().set("sms_checkcode:"+mobile+":conttime","计时器,防止60秒内重复发送",60,TimeUnit.SECONDS);
		//给用户发
		Map<String,String> map = new HashMap<>();
		map.put("mobile",mobile);
		map.put("checkcode",random);
		//通知mq发送
		rabbitTemplate.convertAndSend("sms",map);
		int seconds = DatetimeUtil.secondsFromZero();
		count++;
		redisTemplate.opsForValue().set("sms_checkcode:"+mobile+":count",count+"",seconds,TimeUnit.SECONDS);
		//控制台（测试）
		//System.out.println("验证码:"+random);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
/*		String header = httpServletRequest.getHeader("Authorization");
		if(StringUtils.isEmpty(header)){
			throw new RuntimeException("权限不足");
		}
		if(!header.startsWith("Bearer ")){
			throw new RuntimeException("权限不足");
		}
		String token = header.substring(7);
		try {
			Claims claims = jwtUtil.parseJWT(token);
			String roles = (String)claims.get("roles");
			if(roles==null||!roles.equals("admin")){
				throw new RuntimeException("权限不足");
			}
		} catch (Exception e) {
			throw new RuntimeException("权限不足");
		}
		if(!userDao.existsById(id)){
			throw new RuntimeException("id不存在");
		}*/
		String token = (String)httpServletRequest.getAttribute("claims_admin");
		if(StringUtils.isEmpty(token)){
			throw new RuntimeException("权限不足");
		}
		if(!userDao.existsById(id)){
			throw new RuntimeException("id不存在");
		}
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	public User login(User user) {
		//验证手机号是否正确
		if(!RegexUtils.checkMobile(user.getMobile())){
			throw new RuntimeException("请输入正确的手机号");
		}
		User userResult = userDao.findByMobile(user.getMobile());
		if(userResult==null){
			throw new RuntimeException("您所输入的手机号不存在");
		}
		if(userResult!=null&&bCryptPasswordEncoder.matches(user.getPassword(),
				userResult.getPassword())){
			return userResult;
		}else {
			throw new RuntimeException("密码错误");
		}
	}

    public void updateFanscountAndFollowcount(int number, String userid, String friendid) {
    	userDao.updateFanscount(number,friendid);
    	userDao.updateFollowcount(number,userid);
	}
}
