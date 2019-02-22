package com.tensquare.friend.controller;

import com.netflix.discovery.converters.Auto;
import com.tensquare.entity.Result;
import com.tensquare.entity.ResultEnum;
import com.tensquare.entity.StatusCode;
import com.tensquare.friend.service.FriendService;
import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/22 0022
 */
@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "/{friendId}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable("friendId") String friendId){
        //验证是否登录，并拿到当前用户id
        String token = (String) request.getAttribute("claims_user");
        if(StringUtils.isEmpty(token)){
            return new Result(false,ResultEnum.ERROR.getCode(),"无权访问");
        }
        Claims claims = jwtUtil.parseJWT(token);
        friendService.deleteFriend(claims.getId(),friendId);
        return new Result(true,ResultEnum.OK.getCode(),ResultEnum.OK.getMessage()+":删除");
    }

    /**
     * 添加 喜欢/不喜欢
     * @param friendId
     * @param type 1:喜欢  2:不喜欢
     * @return
     */
    @RequestMapping(value = "/like/{friendId}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable(value = "friendId")String friendId,@PathVariable(value = "type") String type){
        //验证是否登录，并拿到当前用户id
        String token = (String) request.getAttribute("claims_user");
        if(StringUtils.isEmpty(token)){
            return new Result(false,ResultEnum.ERROR.getCode(),"无权访问");
        }
        Claims claims = jwtUtil.parseJWT(token);
        if(type!=null){
            //如果是喜欢
            if("1".equals(type)){
                int flag = friendService.addFriend(claims.getId(),friendId);
                if(flag == 0){
                    return new Result(false,ResultEnum.REP_ERROR.getCode(),"不能重复喜欢");
                }else if(flag == 1){
                    return new Result(false,ResultEnum.OK.getCode(),ResultEnum.OK.getMessage()+"：喜欢");
                }
            }else if("2".equals(type)){
                //如果是不喜欢
                int flag = friendService.addNoFriend(claims.getId(), friendId);
                if(flag == 0){
                    return new Result(false,ResultEnum.REP_ERROR.getCode(),"不能重复不喜欢");
                }else if(flag == 1){
                    return new Result(false,ResultEnum.OK.getCode(),ResultEnum.OK.getMessage()+"：不喜欢");
                }

            }else{
                return new Result(false,ResultEnum.ERROR.getCode(),"参数错误");
            }
        }else {
            return new Result(false,ResultEnum.ERROR.getCode(),"参数错误，type不能为空");
        }




        //判断添加好友还是非好友

        return null;
    }
}
