package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 请求经过网关过滤器之后，头信息会被过滤掉，所以需要从新添加需要的头信息
 * @Author shawn
 * @create 2019/2/25 0025
 */
@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了WebZuul过滤器！");
        //获取request上下文,并通过request获取头信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String header = request.getHeader("Authorization");
        if(!StringUtils.isEmpty(header)){
            //头信息继续往下传
            requestContext.addZuulRequestHeader("Authorization",header);
        }
        return null;
    }
}
