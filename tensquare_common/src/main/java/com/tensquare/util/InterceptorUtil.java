package com.tensquare.util;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/21 0021
 */
public class InterceptorUtil {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    //拦截器验证权限方法
    public void authValid(){
        //无论如何都放行，具体能不能操作还是在具体的操作中去判断
        //拦截器只是负责吧请求头中包含token的令牌进行解析验证
        //无论如何都放行，具体能不能操作还是在具体的操作中去判断
        //拦截器只是负责吧请求头中包含token的令牌进行解析验证
        String header = request.getHeader("Authorization");
        if(!StringUtils.isEmpty(header)){
            if(header.startsWith("shawn ")){
                final String token = header.substring(6);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles != null || "admin".equals(roles)){
                        request.setAttribute("claims_admin",token);
                    }
                    if(roles != null || "user".equals(roles)){
                        request.setAttribute("claims_user",token);
                    }
                }catch (Exception e){
                    throw new RuntimeException("令牌不正确");
                }
            }
        }
    }
}
