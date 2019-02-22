package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description 用户客户端
 * @Author shawn
 * @create 2019/2/22 0022
 */
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 修改粉丝数
     * @param userId
     * @param num
     */
    @RequestMapping(value = "/user/incfanscount/{userId}/{num}",method = RequestMethod.PUT)
    int incFanscount(@PathVariable(value = "userId") String userId, @PathVariable(value = "num")Integer num);

    /**
     * 修改关注数
     * @param userId
     * @param num
     */
    @RequestMapping(value = "/user/incfollow/{userId}/{num}",method = RequestMethod.PUT)
    public int incFollowcount(@PathVariable(value = "userId") String userId,@PathVariable(value = "num")Integer num);
}
