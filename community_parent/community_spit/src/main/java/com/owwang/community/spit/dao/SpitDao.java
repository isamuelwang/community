package com.owwang.community.spit.dao;

import com.owwang.community.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname SpitDao
 * @Description TODO
 * @Date 2020-01-09
 * @Created by WANG
 */
public interface SpitDao extends MongoRepository<Spit,String> {

    public Page<Spit> findByParentid(String parentId, Pageable pageable);
}
