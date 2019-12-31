package com.tensquare.web.Fileter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFileter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";//前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;//优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;//是否执行该过滤器，此处为true,说明需要过滤
    }

    @Override
    public Object run() throws ZuulException {
        //得到上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到request域
        HttpServletRequest request = requestContext.getRequest();
        //得到请求头信息
        String header = request.getHeader("Authorization");
        //判断是否有头信息
        if(header != null && !"".equals(header)){
            //把头信息继续往下传
            requestContext.addZuulRequestHeader("Authorization", header);
        }
        return null;
    }
}
