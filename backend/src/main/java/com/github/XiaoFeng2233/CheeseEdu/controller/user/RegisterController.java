package com.github.XiaoFeng2233.CheeseEdu.controller.user;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/11/2 19:03
 */
@RestController
@RequestMapping("/do/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/sendregistercode.do")
    public Result<String> sendRegisterCode(@RequestParam String mobileNumber){
        return registerService.sendRegisterCode(mobileNumber);
    }
    @PostMapping("/register.do")
    public Result<String> register(@RequestParam String mobileNumber, @RequestParam String code, @RequestParam String username, @RequestParam String password, HttpServletRequest request){
        return registerService.register(username,password,mobileNumber,code,request);
    }
}
