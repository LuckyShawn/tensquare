package com.tensquare.friend.service;

import com.netflix.discovery.converters.Auto;
import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/22 0022
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;
    /**
     * 添加喜欢（好友）
     * @param userId
     * @param friendId
     * @return
     */
    public int addFriend(String userId,String friendId){
        //判断是否已经添加过好友
        Friend friend = friendDao.findByUserIdAndFriendId(userId,friendId);
        if(friend!=null){
            return 0;
        }
        //向喜欢表中添加记录
        friend = new Friend(userId,friendId,"0");
        friendDao.save(friend);
        noFriendDao.deleteByUserIdAndFriendId(userId,friendId);

        //判断对方是否喜欢自己，相互喜欢则双方喜欢记录的isLike改为1
        if(friendDao.findByUserIdAndFriendId(friendId,userId)!=null){
            friendDao.updateIsLike(userId,friendId,"1");
            friendDao.updateIsLike(friendId,userId,"1");
        }
        userClient.incFollowcount(userId,1);
        userClient.incFanscount(friendId,1);
        return 1;
    }

    /**
     * 不喜欢（好友）
     * @param userId
     * @param friendId
     * @return
     */
    public int addNoFriend(String userId,String friendId){
        //判断是否已经添加过不喜欢
        NoFriend noFriend = noFriendDao.findByUserIdAndFriendId(userId,friendId);
        if(noFriend!=null){
            return 0;
        }
        //向喜欢表中添加记录
        noFriend = new NoFriend(userId,friendId);
        noFriendDao.save(noFriend);
        friendDao.deleteByUserIdAndFriendId(userId,friendId);
        return 1;
    }

    /**
     * 删除喜欢
     * @param userId
     * @param friendId
     */
    public void deleteFriend(String userId,String friendId){
        //删除用户的喜欢记录
        friendDao.deleteByUserIdAndFriendId(userId,friendId);
        //更新好友的喜欢记录
        friendDao.updateIsLike(friendId,userId,"0");

        userClient.incFollowcount(userId,-1);
        userClient.incFanscount(friendId,-1);
    }

}
