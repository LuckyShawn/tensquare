package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description   JpaRepository提供了基本的增删改查
 *                 JpaSpecificationExecutor用于做复杂的条件查询
 * @Author shawn
 * @create 2019/2/11 0011
 */
public interface LabelDao extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label> {
}
