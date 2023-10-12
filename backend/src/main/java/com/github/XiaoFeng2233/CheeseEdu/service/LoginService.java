package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/8/2 19:02
 */
public interface LoginService {
    public Result<String> sendLoginVerifyCode(String mobileNumber);
    public Result<String> loginWithVerifyCode(HttpServletRequest request,String mobileNumber,String verifyCode);
    public Result<String> loginWithUsername(HttpServletRequest request,String username, String password);
    public Result<String> sendResetVerifyCode(String mobileNumber);
    public Result<String> resetPassword(String mobileNumber,String verifycode,String newpassword);
}
