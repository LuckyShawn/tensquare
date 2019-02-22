package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/22 0022
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    /**
     * 根据用户id和好友id查询是否已经不喜欢用户
     * @param userId
     * @param friendId
     * @return
     */
    NoFriend findByUserIdAndFriendId(String userId, String friendId);

    /**
     * 添加了喜欢就需要删除不喜欢
     * @param userId
     * @param friendId
     */
    void deleteByUserIdAndFriendId(String userId,String friendId);


}
