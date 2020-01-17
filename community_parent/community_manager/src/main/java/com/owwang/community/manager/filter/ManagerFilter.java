package com.owwang.community.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname ManagerFilter
 * @Description TODO
 * @Date 2020-01-17
 * @Created by WANG
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

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
        //获取上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //获取头信息
        String header = request.getHeader("Authorization");
        if(request.equals("OPTIONS")){
            return null;
        }
        if(request.getRequestURL().indexOf("/admin/login")>0){
            return null;
        }
        if (header!=null&&!"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String)claims.get("roles");
                    if(roles.equals("admin")){
                        requestContext.addZuulRequestHeader("Authorization",header);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    requestContext.setSendZuulResponse(false);
                }
            }
        }
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("{\n" +
                "  \"flag\": false,\n" +
                "  \"code\": 403,\n" +
                "  \"message\": \"权限不足\"\n" +
                "}");
        requestContext.getResponse().setContentType("application/json;charset=UTF8");
        return null;
    }
}
