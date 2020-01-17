package com.owwang.community.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname WebFilter
 * @Description TODO
 * @Date 2020-01-17
 * @Created by WANG
 */
@Component
public class WebFilter extends ZuulFilter {


    //pre表示执行前过滤
    @Override
    public String filterType() {
        return "pre";
    }

    //执行顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    //当前过滤器是否开启
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul过滤器...");
        //向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取header
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if(authorization != null) {
            System.out.println("authorization: " + authorization);
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
