package com.owwang.community.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.owwang.community.util.JwtUtil;

/**
 * @Classname JwtInterceptor
 * @Description TODO
 * @Date 2020-01-14
 * @Created by WANG
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        System.out.println("header:"+header);
        if(!StringUtils.isEmpty(header)){
            System.out.println("=========");
            if(header.startsWith("Bearer ")){
                //得到令牌
                String token = header.substring(7);
                //System.out.println("token:"+token);
                //令牌验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String)claims.get("roles");
                    //System.out.println("roles:"+roles);
                    if(roles!=null&&roles.equals("user")){
                        request.setAttribute("claims_user",token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确");
                }
            }
        }
        return true;
    }

}
