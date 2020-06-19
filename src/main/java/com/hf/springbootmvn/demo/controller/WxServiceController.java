package com.hf.springbootmvn.demo.controller;

import com.hf.springbootmvn.demo.configuration.WxConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/wxService")
public class WxServiceController {

    @Autowired
    WxConfig wxConfig;

    Logger logger = LoggerFactory.getLogger(WxServiceController.class);

    @RequestMapping("createMenu")
    @ResponseBody
    public BaseResult createMenu(){
        String str = "{\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"今日歌曲\",\n" +
                "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"搜索\",\n" +
                "               \"url\":\"http://www.soso.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"赞一下我们\",\n" +
                "               \"key\":\"V1001_GOOD\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";
        String str2 = "{\r\n" +
                "    \"button\": [\r\n" +
                "        {\r\n" +
                "            \"name\": \"扫码\",\r\n" +
                "            \"sub_button\": [\r\n" +
                "                {\r\n" +
                "                    \"type\": \"scancode_waitmsg\",\r\n" +
                "                    \"name\": \"扫码带提示\",\r\n" +
                "                    \"key\": \"rselfmenu_0_0\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                },\r\n" +
                "                {\r\n" +
                "                    \"type\": \"scancode_push\",\r\n" +
                "                    \"name\": \"扫码推事件\",\r\n" +
                "                    \"key\": \"rselfmenu_0_1\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"name\": \"发图\",\r\n" +
                "            \"sub_button\": [\r\n" +
                "                {\r\n" +
                "                    \"type\": \"pic_sysphoto\",\r\n" +
                "                    \"name\": \"系统拍照发图\",\r\n" +
                "                    \"key\": \"rselfmenu_1_0\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                },\r\n" +
                "                {\r\n" +
                "                    \"type\": \"pic_photo_or_album\",\r\n" +
                "                    \"name\": \"拍照或者相册发图\",\r\n" +
                "                    \"key\": \"rselfmenu_1_1\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                },\r\n" +
                "                {\r\n" +
                "                    \"type\": \"pic_weixin\",\r\n" +
                "                    \"name\": \"微信相册发图\",\r\n" +
                "                    \"key\": \"rselfmenu_1_2\",\r\n" +
                "                    \"sub_button\": []\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"name\": \"发送位置\",\r\n" +
                "            \"type\": \"location_select\",\r\n" +
                "            \"key\": \"rselfmenu_2_0\"\r\n" +
                "        }\r\n" +
                "    ]\r\n" +
                "}";
        BaseResult baseResult = MenuAPI.menuCreate(TokenManager.getDefaultToken(), str2);
        return baseResult;
    }

    // 重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    @RequestMapping("validate")
    @ResponseBody
    public void validate(@RequestParam Map<String,String> param, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        logger.info("==debug validate==");
        //接受参数
        String signature = param.get("signature");
        String timestamp = param.get("timestamp");
        String nonce = param.get("nonce");
        String echostr = param.get("echostr");

        //判断传来的字符不能为空
        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp)) {
            outputStreamWrite(outputStream, "faild request");
            return;
        }

        //判断echostr不为空
        if (echostr != null) {
            outputStreamWrite(outputStream, echostr);
            return;
        }

        //按照微信文档合成排序后的字符串
        String tokenString = wxConfig.getTokenString();
        String[] strings = {timestamp, nonce, tokenString};
        Arrays.sort(strings);
        String implode = null;
        for (String str: strings){
            implode += str;
        }

        //使用sha1加密
        if(!signature.equals(SignatureUtil.generateEventMessageSignature(tokenString, timestamp, nonce))){
            System.out.println("The request signature is invalid");
            return;
        }

        if (inputStream != null) {
            // 转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);

            logger.info("eventMessage:" + ToStringBuilder.reflectionToString(eventMessage));
            String key = eventMessage.getFromUserName() + "__" + eventMessage.getToUserName() + "__" + eventMessage.getMsgId() + "__" + eventMessage.getCreateTime();


            if (expireKey.exists(key)) {
                // 重复通知不作处理
                System.err.println("重复通知不作处理");
                return;
            } else {
                expireKey.add(key);
            }
            logger.info(TokenManager.getDefaultToken());

//            XMLTextMessage xmlTextMessage2 = new XMLTextMessage(eventMessage.getFromUserName(), eventMessage.getToUserName(),
//                    "请先<a href='http://weixin.duozuiyu.com/profile/my'>完善一下信息</a>");
            XMLImageMessage xmlImageMessage = new XMLImageMessage(eventMessage.getFromUserName(), eventMessage.getToUserName(),
                    "WMe8pSkdH5CGNcYjNjkHEO9WhQGfZzdMTztQ98U4HHf1JbkS7gQ0zKXoF6O0OfvN");
            xmlImageMessage.outputStreamWrite(outputStream);
            return;
        }
        outputStreamWrite(outputStream, "echostr");
    }

    private boolean outputStreamWrite(OutputStream outputStream, String text) {
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
