package com.github.XiaoFeng2233.CheeseEdu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {
    @RequestMapping(value = {"/","/home"})
    public String home(){
        return "Home";
    }
    @RequestMapping(value = {"/admin"})
    public String admin(){
        return "Admin";
    }
    @RequestMapping(value = {"/m"})
    public String mobile(){
        return "Mobile";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String callback(){
        System.out.println("测试被调用");
        return "heeloo";
    }
}
