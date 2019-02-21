package com.tensquare.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.tensquare.util.InterceptorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/20 0020
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private InterceptorUtil interceptorUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        interceptorUtil.authValid();
        return true;
    }

}
