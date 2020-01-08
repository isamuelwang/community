package com.owwang.community.base.service;

import com.owwang.community.base.dao.LabelDao;
import com.owwang.community.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname labelService
 * @Description TODO
 * @Date 2020-01-06
 * @Created by WANG
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             * @param root 根对象。相类于where 类名=label.getId中的类名
             * @param query group by order by(一般不用这个参数）
             * @param cb 用于封装条件对象
             * @return javax.persistence.criteria.Predicate
             * @Date 2020-01-06
             * @auther Samuel
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals((label.getLabelname()))){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),
                            "%" + label.getLabelname() + "%");//where labelname like "%label.getLabelname%"
                    list.add(predicate);
                }
                if(label.getState()!=null&&!"".equals(label.getState())){
                    //where 左边（root） = 右边
                    cb.equal(root.get("state").as(String.class),label.getState());
                }
                Predicate[] parr = new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr);//where labelname like %xx% and state = "1"
            }
        });
    }

    /**
     * 分页查询
     * @Description TODO
     * @return org.springframework.data.domain.Page<com.owwang.community.base.pojo.Label>
     * @Date 2020-01-06
     * @auther Samuel
     */
    public Page<Label> pageQuery(Label label, Integer page, Integer size) {
        //初始化分页信息
        if(page==null){
            page=0;
        }
        if(size==null){
            size=10;
        }
        //封装分页对象
        Pageable pageable = PageRequest.of(page-1,size );
        //查询
        return labelDao.findAll(new Specification<Label>() {
            /**
             * @param root 根对象。相类于where 类名=label.getId中的类名
             * @param query group by order by(一般不用这个参数）
             * @param cb 用于封装条件对象
             * @return javax.persistence.criteria.Predicate
             * @Date 2020-01-06
             * @auther Samuel
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals((label.getLabelname()))){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),
                            "%" + label.getLabelname() + "%");//where labelname like "%label.getLabelname%"
                    list.add(predicate);
                }
                if(label.getState()!=null&&!"".equals(label.getState())){
                    //where 左边（root） = 右边
                    cb.equal(root.get("state").as(String.class),label.getState());
                }
                Predicate[] parr = new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr);//where labelname like %xx% and state = "1"
            }
        },pageable);
    }
}
