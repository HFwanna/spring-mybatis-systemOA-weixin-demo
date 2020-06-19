package com.hf.springbootmvn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hf.springbootmvn.demo.service.Sservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CController {

    public String index() {
        return "index";
    }


}
