package com.hf.springbootmvn.demo.controller;

import com.hf.springbootmvn.demo.configuration.WxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class WxAuthController {
    private static final Logger logger = LoggerFactory.getLogger(WxAuthController.class);

    @Autowired
    WxConfig wxConf;

    @RequestMapping("")
    public String list(@RequestParam Map<String,String> param, HttpServletRequest request){
        // Code
        String code = param.get("code");
        // 获取User对象
        SnsToken stoken = SnsAPI.oauth2AccessToken(wxConf.getAppID(), wxConf.getAppsecret(), code);
        User user = SnsAPI.userinfo(stoken.getAccess_token(), wxConf.getAppID(), "zh_CN");
        logger.info("user:" + user);
        logger.info(user.getHeadimgurl());

        // User写入Session
        request.getSession().setAttribute("account", user);
        // 访问受限的那个URI
        logger.info("uri1: " + param.get("uri"));
        return "redirect:" + param.get("uri");
    }
}
