package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.Map;

import com.tensquare.entity.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
@RefreshScope
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Value("${user.name}")
	private String name;


	/**
	 * 修改粉丝数
	 * @param userId
	 * @param num
	 */
	@RequestMapping(value = "/incfanscount/{userId}/{num}",method = RequestMethod.PUT)
	public int incFanscount(@PathVariable(value = "userId") String userId,@PathVariable(value = "num")Integer num){
		userService.incFanscount(userId,num);
		return 1;
	}

	/**
	 * 修改关注数
	 * @param userId
	 * @param num
	 */
	@RequestMapping(value = "/incfollow/{userId}/{num}",method = RequestMethod.PUT)
	public int incFollowcount(@PathVariable(value = "userId") String userId,@PathVariable(value = "num")Integer num){
		userService.incFollowcount(userId,num);
		return 1;
	}

	/**
	 * 用户登陆
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody Map<String,String> map){
		User user = userService.findByMobileAndPassword(map.get("mobile"),map.get("password"));
		if(user == null){
			return new Result(false,ResultEnum.LOGIN_ERROR.getCode(),ResultEnum.LOGIN_ERROR.getMessage());
		}
		//创建token并返回信息给调用方
		String token = jwtUtil.createJWT(user.getId(),user.getNickname(),"user");
		Map<String,String> result = new HashMap<>();
		result.put("token",token);
		result.put("nickname",user.getNickname());
		result.put("avatar",user.getAvatar());
		return new Result(true,ResultEnum.OK.getCode(),ResultEnum.OK.getMessage(),result);
	}

	/**
	 * 发送短信验证码
	 * @param mobile
	 */
	@RequestMapping(value="/sendsms/{mobile}",method=RequestMethod.GET)
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	/**
	 * 增加
	 * @param user 用户实体
	 * @param code 用户填写的验证码
	 */
	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result register(@RequestBody User user,@PathVariable String code){
		userService.add(user,code);
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		System.out.println("user.name:"+name);
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
