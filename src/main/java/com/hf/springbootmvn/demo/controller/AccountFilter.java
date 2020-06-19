package com.hf.springbootmvn.demo.controller;

import com.hf.springbootmvn.demo.entity.Account;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
public class AccountFilter implements Filter {
    //某些路径不拦截
    public static final String[] IGNORE_URI = {"/wxService/createMenu","/wxService/validate","/static", "/account/modify_validate", "/account/register", "/account/login", "/js", "/images", "/css", "/account/validataAccount", "/account/index"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //某些路径不拦截
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
        //拦截修改密码,等于"1"
        if (servletPath.equals("/account/modify")) {
            if (((HttpServletRequest) servletRequest).getSession().getAttribute("newAccountPassword") != null
                    && ((Account) (((HttpServletRequest) servletRequest).getSession().getAttribute("newAccountPassword"))).getNickName() == "1") {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            ((HttpServletResponse) servletResponse).sendRedirect("/account/modify_validate");
            return;
        }
        for (String uri : IGNORE_URI) {
            //所有URI开头的和URI资源都放行
            if (uri.equals(servletPath) || servletPath.startsWith(uri)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        //过滤掉非session 的访问，-》login.html    ctrl +alt + t  ==》快捷surround by
        if (((HttpServletRequest) servletRequest).getSession().getAttribute("account") == null) {
            System.out.println("to login");
            ((HttpServletResponse) servletResponse).sendRedirect("/account/login");
            return;
        }

        //有session，放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
