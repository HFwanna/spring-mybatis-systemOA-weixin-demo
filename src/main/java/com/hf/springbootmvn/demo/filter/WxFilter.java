package com.hf.springbootmvn.demo.filter;

import com.hf.springbootmvn.demo.configuration.WxConfig;
import com.hf.springbootmvn.demo.httpTools.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WxFilter implements Filter {
    public static final Logger logger = LoggerFactory.getLogger(WxFilter.class);

    @Autowired
    WxConfig wxConfig;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //1.获取session判断
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
       if (httpServletRequest.getSession().getAttribute("account") != null){
//           logger.info("acount=" + ((User)httpServletRequest.getSession().getAttribute("account")).getNickname());
           filterChain.doFilter(servletRequest,servletResponse);
           return;
       }
        //2.没有的话执行通过access_token+用户的openid方式获取用户信息，这种方式要求用户登录公众号才能获取到openid
//       else{
//               String access_token = TokenManager.getDefaultToken();
//               String openid = httpServletRequest.getParameter("openid");
//               //http GET请求方式获取用户信息
//               String account_message = HttpRequestUtil.sendGet("https://api.weixin.qq.com/cgi-bin/user/info?access_" +
//                       "token=" +
//                       + "&openid=" + openid + "&lang=zh_CN", null);
//               logger.info("access_token: " + access_token);
//               logger.info("openid: " + openid);
//               logger.info("account_message: " + account_message);
//       }
       //3.通过网页授权方式获取用户信息
        else{
           logger.info("获取授权.....");
           String uri = httpServletRequest.getRequestURI();
           logger.info("uri: " + uri);
//            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
//                    "appid=" + wxConfig.getAppID() + "&redirect_uri="+ uri +"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
           String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxConfig.getAppID() +
                   "&redirect_uri=http://v9r4ec.natappfree.cc/auth?uri="
                   + uri + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

            httpServletResponse.sendRedirect(url);
            return;
       }

//       filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("WxFilter init ......");
    }
}
