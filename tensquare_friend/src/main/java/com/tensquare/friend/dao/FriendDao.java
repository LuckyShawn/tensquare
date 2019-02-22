package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/22 0022
 */
public interface FriendDao extends JpaRepository<Friend,String> {

    /**
     * 根据用户id和好友id查询是否已经喜欢用户
     * @param userId
     * @param friendId
     * @return
     */
    Friend findByUserIdAndFriendId(String userId,String friendId);

    /**
     * 修改为喜欢（可以id互换更新为互相喜欢）
     * @param userId
     * @param friendId
     * @param isLike
     */
    @Modifying
    @Query(value = "update tb_friend set is_like = ?3 where user_id = ?1 AND friend_id = ?2",nativeQuery = true)
    void updateIsLike(String userId,String friendId,String isLike);

    /**
     *  添加了不喜欢，就要删除喜欢
     * @param userId
     * @param friendId
     */
    void deleteByUserIdAndFriendId(String userId,String friendId);
}
