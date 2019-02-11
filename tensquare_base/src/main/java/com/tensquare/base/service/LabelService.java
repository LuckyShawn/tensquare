package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 标签业务逻辑类
 * @Author shawn
 * @create 2019/2/11 0011
 */
@Service
public class LabelService {
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private LabelDao labelDao;

    /**
     * 查询全部标签
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     *
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 增加标签
     *
     * @param label
     */

    public void add(Label label) {
        label.setId(idWorker.nextId() + "");//设置ID
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     *
     * @param id
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 分页条件查询
     * @param searchMap
     * @return
     */
    public Page<Label> findSearch(Label searchMap, Integer page, Integer size){
        Specification specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return labelDao.findAll(specification,pageRequest);
    }

    /**
     * 构建查询条件
     *
     * @param searchMap
     * @return
     */
    private Specification<Label> createSpecification(Label searchMap) {

        Specification<Label> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();

            if (searchMap.getLabelname()!= null && !"".equals(searchMap.getLabelname())) {
                predicateList.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%" + (String) searchMap.getLabelname() + "%"));
            }

            if(searchMap.getState()!=null && !"".equals(searchMap.getState())){
                predicateList.add(criteriaBuilder.equal(root.get("state").as(String.class), (String)searchMap.getState() ) );
            }

            if(searchMap.getRecommend()!=null && !"".equals(searchMap.getRecommend())){
                predicateList.add(criteriaBuilder.equal(root.get("recommend").as(String.class), (String)searchMap.getRecommend() ) );
            }
            return criteriaBuilder.and( predicateList.toArray( new Predicate[predicateList.size()]) );
        };
        return specification;
    }
}
