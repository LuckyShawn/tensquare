package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 修改粉丝数
     * @param userId
     * @param num
     */
    @Modifying
    @Query(value = "update tb_user set fanscount = fanscount+?2 where id = ?1",nativeQuery = true)
    void incFanscount(String userId,Integer num);

    /**
     * 修改关注数
     * @param userId
     * @param num
     */
    @Modifying
    @Query(value = "update tb_user set followcount = followcount+?2 where id = ?1",nativeQuery = true)
    void incFollowcount(String userId,Integer num);
}
