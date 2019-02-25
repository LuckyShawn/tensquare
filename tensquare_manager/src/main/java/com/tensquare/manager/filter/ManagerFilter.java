package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 网关过滤器
 * @Author shawn
 * @create 2019/2/25 0025
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * pre 前置过滤器
     * post 后置过滤器
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的优先级，越小越优先，0最优先
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器，true为执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的执行逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("Manager网关过滤器执行了！");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //分发请求必须放行
        if ("OPTIONS".equals(request.getMethod())){
            return null;
        }
        //登录请求放行
        String url = request.getRequestURL().toString();
        if(url.indexOf("login")>0){
            System.out.println("登录页面："+url);
            return null;
        }
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("shawn ")){
            String token = header.substring(6);
            try {
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null){
                    if("admin".equals(claims.get("roles"))){
                        requestContext.addZuulRequestHeader("Authorization",header);
                        System.out.println("Token信息验证通过，并添加了头信息："+header);
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                requestContext.setSendZuulResponse(false);//终止运行
            }
        }
        //否则
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(401);//http状态码
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");

        return null;
    }
}
