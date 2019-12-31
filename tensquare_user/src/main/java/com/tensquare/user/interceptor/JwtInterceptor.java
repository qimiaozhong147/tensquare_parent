package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//这里也可以继承HandleInterceptorAdapter
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    //访问前拦截
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        String authHeader = request.getHeader("Authorization");//获取请求头
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
           try {
               Claims claims = jwtUtil.parseJWT(token);//解析token
               if(claims != null && "".equals(claims)){
                   if("admin".equals(claims.get("roles"))){//如果是管理员
                       request.setAttribute("claims_admin", claims);
                   }
                   if("user".equals(claims.get("roles"))){//如果是普通用户
                       request.setAttribute("claims_user", claims);
                   }
               }
           }catch (Exception e){
               throw new RuntimeException("token已过期");
           }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
