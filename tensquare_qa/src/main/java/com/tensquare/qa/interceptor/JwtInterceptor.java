package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.InterceptorUtil;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/20 0020
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    static {
        System.out.print("load class JwtInterceptor");
    }

    @Autowired
    private InterceptorUtil interceptorUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        interceptorUtil.authValid();
        return true;
    }

}
