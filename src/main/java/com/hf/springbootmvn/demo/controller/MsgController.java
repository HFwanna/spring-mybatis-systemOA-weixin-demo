package com.hf.springbootmvn.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;
import weixin.popular.support.TokenManager;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/msg")
public class MsgController {
    private static final Logger logger = LoggerFactory.getLogger(MsgController.class);

    @RequestMapping("")
    public TemplateMessageResult list(@RequestParam Map<String, String> param, HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
//        String fromUserName = eventMessage.getFromUserName();

        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl("http://v9r4ec.natappfree.cc/ticket");
        templateMessage.setTemplate_id("tDBKS-UYway95KgNdNrGRvGKIeBYjxRTjzg7L2Zl5js");
        templateMessage.setTouser("oQL1s1fHqZ4tTjIXbdOdXcboHa_w");
        TemplateMessageResult templateMessageResult = MessageAPI.messageTemplateSend(TokenManager.getDefaultToken(), templateMessage);
        return templateMessageResult;
    }
}
