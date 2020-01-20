package com.owwang.community.spit.service;

import com.owwang.community.spit.dao.SpitDao;
import com.owwang.community.spit.pojo.Spit;
import com.owwang.community.entity.Result;
import com.owwang.community.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.owwang.community.util.IdWorker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Classname SpitService
 * @Description TODO
 * @Date 2020-01-09
 * @Created by WANG
 */
@Service
@Transactional
public class SpitService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<Spit> findByParentId(String parentId,int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentId,pageable);
    }

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime = dataFormat.format(new Date());
        spit.setPublishtime(formatTime);//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //操作父节点
        String parentId = spit.getParentid();
        if(parentId!=null&&!parentId.equals("")){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(parentId));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit){
        spitDao.save(spit);
    }

    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    public Result thumbup(String spitId) {
/*         Spit spit = spitDao.findById(spitId).get();
         if(spit!=null){
             spit.setThumbup(spit.getThumbup()==null?1:+spit.getThumbup()+1);
             spitDao.save(spit);
         }*/
        //方式二：使用原生mongo命令 db.spit.update({_id:1},{$inc:{thumbup:1}})
        //判断用户是否已经点赞（暂时先把userid写死）
        String userId = "111";
        Object isThumbup = redisTemplate.opsForValue().get("thumbupSpit:" + userId+":"+spitId);
        if(isThumbup!=null){
            return new Result(false, StatusCode.ERROR,"请勿重复点赞");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
        //点赞信息存入redis
        redisTemplate.opsForValue().set("thumbupSpit:" + userId +":"+spitId,true);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
