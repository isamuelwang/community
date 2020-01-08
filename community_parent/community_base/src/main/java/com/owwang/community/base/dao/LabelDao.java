package com.owwang.community.base.dao;

import com.owwang.community.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Classname LabelDao
 * @Description TODO
 * @Date 2020-01-06
 * @Created by WANG
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor {

}
