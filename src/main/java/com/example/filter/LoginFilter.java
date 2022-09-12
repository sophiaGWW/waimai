package com.example.filter;

import com.alibaba.fastjson2.JSON;
import com.example.common.BaseContext;
import com.example.common.R;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @create 2022-09-02 16:10
 */
@WebFilter(urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
            //设置需要放行的请求路径
        String[] urls =new String[]{
                "/backend/**",
                "/front/**",
                "/employee/login",
                "/employee/logout",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        for (String url:urls
             ) {
            boolean match = PATH_MATCHER.match(url, uri);
            if (match){
                log.info("本次请求{}不需要处理",uri);
                filterChain.doFilter(request,response);
                return ;
            }

        }
        //判断登录状态
        if (request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));
            Long id = (Long) request.getSession().getAttribute("employee");
            BaseContext.setId(id);
            filterChain.doFilter(request,response);
            return;
        }
        //4-2、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setId(userId);

            filterChain.doFilter(request,response);
            return;
        }
        //未登录
        log.info("用户未登录");
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
}
