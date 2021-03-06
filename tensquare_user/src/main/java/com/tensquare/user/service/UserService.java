package com.tensquare.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tensquare.util.IdWorker;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import com.tensquare.util.JwtUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 修改粉丝数
     * @param userId
     * @param num
     */
    @Transactional
    public void incFanscount(String userId,Integer num){
        userDao.incFanscount(userId,num);
    }

    /**
     * 修改关注数
     * @param userId
     * @param num
     */
    @Transactional
    public void incFollowcount(String userId,Integer num){
        userDao.incFollowcount(userId,num);
    }

    /**
     * 根据手机号和密码查询用户 并匹配密码
     * @param mobile
     * @param password
     * @return
     */
    public User findByMobileAndPassword(String mobile, String password){
       User user = userDao.findByMobile(mobile);
       if(bCryptPasswordEncoder.matches(password,user.getPassword())){
           return user;
       }else {
           return null;
       }
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    public void sendSms(String mobile) {
        //生成6为随机数验证码
//		Random random = new Random();
//		int max = 999999;
//		int min = 100000;
//		int code = random.nextInt(999999);
//		if(code < 100000){
//			code += min;
//		}
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println("短信验证码是：" + code);
        //验证码放入缓存中  10分钟后过期
        redisTemplate.opsForValue().set("SmsCode_" + mobile, code, 10, TimeUnit.MINUTES);

        //3.将验证码和手机号发动到rabbitMQ中
        Map map = new HashMap();
        map.put("code", "code" + code);
        map.put("mobile", mobile);
        rabbitTemplate.convertAndSend("sms", map);

    }

    /**
     * 增加
     *
     * @param user 用户
     * @param code 用户填写的验证码
     */
    @Transactional
    public void add(User user, String code) {
        //判断验证码是否正确
        String syscode = (String) redisTemplate.opsForValue().get("SmsCode_" + user.getMobile());
        //提取系统正确的验证码
        if (syscode == null) {
            throw new RuntimeException("请点击获取短信验证码");
        }
        if (!syscode.equals(code)) {
            throw new RuntimeException("验证码输入不正确");
        }
        if(!StringUtils.isEmpty(user.getPassword())){
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        user.setId(idWorker.nextId() + "");
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期
        userDao.save(user);
    }


    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        //删除钱需要验证权限
        String claims_admin = (String) request.getAttribute("claims_admin");
        if(StringUtils.isEmpty(claims_admin)){
            throw new RuntimeException("权限不足");
        }
        userDao.deleteById(id);
    }


    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
