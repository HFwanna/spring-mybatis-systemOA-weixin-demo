package com.hf.springbootmvn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hf.springbootmvn.demo.entity.Account;
import com.hf.springbootmvn.demo.service.Sservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * session变量 ：account -> String 用户名loginName
 * account_object -> Account account 对象
 * newAccountPassword  ->设置新用户密码时，要对account 的nickName设置标志位为1，所以创建这个新的account对象对应属性nickName='1'
 */

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    Sservice sservice;

    //登录输入界面
    @RequestMapping("/login")
    public String login() {
        return "account/login";
    }

    //登录账户密码校验
    @RequestMapping("/validataAccount")
    @ResponseBody
    public String validata(HttpServletRequest request, String loginName, String password) {
        //获取用户名密码校验

        //校验逻辑：封装于service内，service返回account，判断account是否为null，不为null返回index
        Account account = sservice.findByLoginNameAndPassword(loginName, password);
        if (account != null) {
            //保存到session
            request.getSession().setAttribute("account", loginName);
            request.getSession().setAttribute("account_object", account);
            return "success";
        }
        System.out.println(request.getSession().getAttribute("account"));
        return "login";
    }

    //主页
    @RequestMapping("/index")
    public String index() {
        return "account/list";
    }

    //用户列表数据显示
    @RequestMapping("/list")
    public String list(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Account> all = sservice.findAll();
        PageInfo<Account> pageInfo = new PageInfo<>(all, 5);
        System.out.println("qqqqqqqqqqqqqqqqqqqqqq" + pageInfo.getList().get(0));
        model.addAttribute("page", pageInfo);
        model.addAttribute("type", "account");
        //权限控制是否显示删除按钮
        Account account = (Account) request.getSession().getAttribute("account_object");
        if (account.getRole().equals("admin")) {
            model.addAttribute("admin", true);
        } else {
            model.addAttribute("admin", false);
        }
        return "index";
    }

    //注册校验
    @RequestMapping("/register_validate")
    @ResponseBody
    public String registerV(String loginName, String password, Model model, HttpServletRequest request) {
        boolean register = sservice.register(loginName, password);
        if (register) {
            System.out.println("zhuce");
            model.addAttribute("result", "注册成功，请重新输入登录账户密码");
            request.getSession().setAttribute("result", "注册成功，请重新输入登录账户密码");
            return "success";
        }
        System.out.println("zhuceshibai");
        model.addAttribute("result", "注册失败，请重新输入登录账户密码");
        return "fail";
    }

    //到注册界面
    @RequestMapping("/register")
    public String register() {
        return "account/register";
    }

    //到修改密码校验界面
    @RequestMapping("/modify_validate")
    public String modify_validate(String password, HttpServletRequest request, Model model) {
        String loginName = (String) request.getSession().getAttribute("account");
        if (password == null) {
            //到先输入旧密码逻辑
            return "account/modify_validate";
        }
        //判断密码是否正确
        Account account = sservice.findByLoginNameAndPassword(loginName, password);
        if (account != null) {
            account.setNickName("1");//模拟一个字段，如果是1就表示可以进入修改密码逻辑
            request.getSession().setAttribute("newAccountPassword", account);
            return "account/modify";
        }
        model.addAttribute("result", "输入密码不正确，请重新输入");
        return "account/modify_validate";
    }

    //到设置密码逻辑
    @RequestMapping("/modify")
    public String modify(String password, HttpServletRequest request, Model model) {
        String loginName = (String) request.getSession().getAttribute("account");
        //修改密码
        boolean tf = sservice.updatePassword(loginName, password);
        if (tf) {
            ((Account) request.getSession().getAttribute("newAccountPassword")).setNickName("0");
            return "index";
        }
        model.addAttribute("result", "输入密码不正确，请重新输入");
        System.out.println("???");
        return "account/modify_validate";
    }

    //根据主键id删除用户数据
    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteById(Integer id, HttpServletRequest request) {
        //删除id权限控制，只有admin才能删除
        if (((Account) request.getSession().getAttribute("account_object")).getRole().equals("admin")) {
            return sservice.deleteById(id);
        }
        return "fail";
    }

    //退出登录
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("account");
        request.getSession().removeAttribute("account_object");
        return "index";
    }

    //访问个人信息，显示个人图片，没有的话默认一个
    @RequestMapping("/profile")
    public String profile(HttpServletRequest request, Model model) {
        System.out.println("访问：/account/profile");
        //根据session找到account
        Account account_object = (Account) request.getSession().getAttribute("account_object");
        String pic = account_object.getPic() == null ? "123.jpg" : account_object.getPic();//默认使用123.jpg
        System.out.println("pic = " + pic);
        model.addAttribute("pic", "/upload/" + pic);
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(), "static/upload/");
            System.out.println(upload.getAbsolutePath());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ;
        return "account/profile";
    }

    /**
     * 中文字符
     *
     * @param filename
     * @param password
     * @return
     */
    @RequestMapping("/fileUploadController")
    @ResponseBody()
    public String fileUpload(HttpServletRequest request, MultipartFile filename, String password) {
        System.out.println("password:" + password);
        System.out.println("file:" + filename.getOriginalFilename());
        //图片重命名
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        //获取后缀名
        String originalFilename = filename.getOriginalFilename();
        String[] split = filename.getOriginalFilename().split(".");
        int length = filename.getOriginalFilename().split("\\.").length - 1;
        String ext = filename.getOriginalFilename().split("\\.")[length];

        try {
            //拿到项目访问路径
//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            System.out.println("ResourceUtils.getURL(\"classpath:\").getPath():" + path);
            //上传路径
            File upload = new File("d:/desktop/tempory", "static/upload/");

            System.out.println("upload:" + upload);
            //保存文件到指定路径
            filename.transferTo(new File(upload + "/" + name + "." + ext));

            //保存图片名字到数据库
            Account account_object = (Account) request.getSession().getAttribute("account_object");
            account_object.setPic(name + "." + ext);
            if (!sservice.save(account_object)) {
                return "上传失败";
            }
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "上传成功";
    }


}
