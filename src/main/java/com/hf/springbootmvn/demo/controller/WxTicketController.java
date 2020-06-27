package com.hf.springbootmvn.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hf.springbootmvn.demo.httpTools.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import weixin.popular.api.QrcodeAPI;
import weixin.popular.bean.qrcode.QrcodeTicket;
import weixin.popular.support.TokenManager;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/ticket")
public class WxTicketController {
    private static final Logger logger = LoggerFactory.getLogger(WxTicketController.class);
    @RequestMapping("")
    public String getTicket(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("==ticket debug==");
        /*第一种自己写API
        String defaultToken = TokenManager.getDefaultToken();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+defaultToken;
        String result = HttpRequestUtil.sendPost(url, "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}} ");
        JSONObject jsonObject = JSON.parseObject(result);

        String ticket = jsonObject.getString("ticket");
        logger.info(jsonObject.toJSONString());
        logger.info("ticket:" + ticket);
        model.addAttribute("result_ticket","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
         return "weixin/ticket";
        */

        //第二种：使用微信API
        QrcodeTicket qrcodeTicket = QrcodeAPI.qrcodeCreateTemp(TokenManager.getDefaultToken(), 60000, "111");
        String ticket = qrcodeTicket.getTicket();
        model.addAttribute("result_ticket","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
        return "weixin/ticket";

       /*第三种：可以使用流直接输入页面
        QrcodeTicket qrcodeTicket = QrcodeAPI.qrcodeCreateTemp(TokenManager.getDefaultToken(), 60000, "111");
       BufferedImage showqrcode = QrcodeAPI.showqrcode(qrcodeTicket.getTicket());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(showqrcode,"png",bos);
        byte[] bytes = bos.toByteArray();
        response.getOutputStream().write(bytes);*/
//        return "";
    }
}
