package com.github.XiaoFeng2233.CheeseEdu.controller.user;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.LoginService;
import com.github.XiaoFeng2233.CheeseEdu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/11/2 19:01
 */
@RestController
@RequestMapping("/do/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/sendloginverifycode.do")
    public Result<String> sendLoginVerifyCode(@RequestParam String mobileNumber) {
        return loginService.sendLoginVerifyCode(mobileNumber);
    }

    @PostMapping("/loginwithverifycode.do")
    public Result<String> loginWithVerifyCode(@RequestParam String mobileNumber,@RequestParam String verifyCode,HttpServletRequest request){
        return loginService.loginWithVerifyCode(request,mobileNumber,verifyCode);
    }

    @PostMapping("/loginwithusername.do")
    public Result<String> loginWithUsername(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        return loginService.loginWithUsername(request,username,password);
    }

    @PostMapping("/sendresetverifycode.do")
    public Result<String> sendResetVerifyCode(@RequestParam String mobileNumber){
        return loginService.sendResetVerifyCode(mobileNumber);
    }

    @PostMapping("/resetpassword.do")
    public Result<String> restPassword(@RequestParam String mobileNumber,@RequestParam String verifyCode,@RequestParam String newpassword){
        return loginService.resetPassword(mobileNumber,verifyCode,newpassword);
    }
}
