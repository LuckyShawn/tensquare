package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/25 0025
 */
//@Component
public class MTestFilter extends ZuulFilter {
    /**
     * pre 前置过滤器
     * post 后置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return "post";
    }

    /**
     * 过滤器的优先级，越小越优先，0最优先
     * @return
     */
    @Override
    public int filterOrder() {
        return 5;
    }

    /**
     * 是否执行该过滤器，true为执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的执行逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("MTest网关过滤器执行了！");
        return null;
    }
}
