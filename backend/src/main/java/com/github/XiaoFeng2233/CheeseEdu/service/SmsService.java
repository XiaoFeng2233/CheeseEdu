package com.github.XiaoFeng2233.CheeseEdu.service;

/**
 * 阿里云和腾讯云审核不通过,已废弃
 */
public interface SmsService {
    Boolean sendLoginCode(String phoneNumber,String verifyCode);
    Boolean sendRegCode(String phoneNumber,String verifyCode);
    Boolean sendForgetCode(String phoneNumber,String verifyCode);
    Boolean sendResetCode(String phoneNumber,String verifyCode);
}
