package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/8/2 19:44
 */

public interface RegisterService {
    Result<String> sendRegisterCode(String number);
    Result<String> register(String username, String password, String number, String code, HttpServletRequest request);
}
