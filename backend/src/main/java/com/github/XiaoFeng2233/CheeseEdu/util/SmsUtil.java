package com.github.XiaoFeng2233.CheeseEdu.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.github.XiaoFeng2233.CheeseEdu.dao.SmsConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.SmsConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.SmsConfigService;
import com.github.XiaoFeng2233.CheeseEdu.service.impl.AliyunSmsService;
import com.github.XiaoFeng2233.CheeseEdu.service.impl.TencentSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@Slf4j
public class SmsUtil {

    private final String httpUrl = "http://api.smsbao.com/sms";
    private final String loginTemplate = "【%s】您的登录验证码是%s，验证码2分钟内有效。若非本人操作请忽略此消息。";
    private final String resetTemplate = "【%s】您正在进行更换手机号操作，您的验证码是%s，验证码2分钟内有效。若非本人操作请忽略此消息。";
    private final String forgetTemplate = "【%s】您正在进行找回密码操作，您的验证码是%s，验证码2分钟内有效。若非本人操作请忽略此消息。";
    private final String registerTemplate = "【%s】您的注册验证码是%s，验证码2分钟内有效。若非本人操作请忽略此消息。";

    @Autowired
    private SmsConfigService smsConfigService;

    public Boolean sendLoginCode(String phoneNumber,String verifyCode) {
        SmsConfig smsConfig = smsConfigService.getSmsConfig();
        if (smsConfig == null){
            log.error("短信发送失败,还未配置发信平台");
            return false;
        }
        if (StrUtil.hasBlank(smsConfig.getSmsPlatform()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPassword()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoUsername()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPrefix())){
            log.error("短信发送失败,请进入后台检查配置是否有缺漏");
            return false;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("u=").append(smsConfig.getDuanxinbaoUsername()).append("&");
        stringBuffer.append("p=").append(SecureUtil.md5(smsConfig.getDuanxinbaoPassword())).append("&");
        stringBuffer.append("m=").append(phoneNumber).append("&");
        String template = String.format(loginTemplate,smsConfig.getDuanxinbaoPrefix(),verifyCode);
        try {
            stringBuffer.append("c=").append(URLEncoder.encode(template, "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest httpRequest = HttpRequest.get(httpUrl + "?" + stringBuffer);
        HttpResponse execute = httpRequest.execute();
        if (execute.body().equalsIgnoreCase("0")){
            return true;
        }else if (execute.body().equalsIgnoreCase("30")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("40")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("41")){
            log.error("短信发送失败,短信宝账号余额不足");
            return false;
        }else if (execute.body().equalsIgnoreCase("43")){
            log.error("短信发送失败,短信宝IP地址限制");
            return false;
        }else if (execute.body().equalsIgnoreCase("50")){
            log.error("短信发送失败,短信含有敏感词汇");
            return false;
        }else if (execute.body().equalsIgnoreCase("51")){
            log.error("短信发送失败,手机号码输入错误");
            return false;
        }else{
            return false;
        }
    }

    public Boolean sendRegCode(String phoneNumber,String verifyCode){

        SmsConfig smsConfig = smsConfigService.getSmsConfig();
        if (smsConfig == null){
            log.error("短信发送失败,还未配置发信平台");
            return false;
        }
        if (StrUtil.hasBlank(smsConfig.getSmsPlatform()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPassword()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoUsername()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPrefix())){
            log.error("短信发送失败,请进入后台检查配置是否有缺漏");
            return false;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("u=").append(smsConfig.getDuanxinbaoUsername()).append("&");
        stringBuffer.append("p=").append(SecureUtil.md5(smsConfig.getDuanxinbaoPassword())).append("&");
        stringBuffer.append("m=").append(phoneNumber).append("&");
        String template = String.format(registerTemplate,smsConfig.getDuanxinbaoPrefix(),verifyCode);
        try {
            stringBuffer.append("c=").append(URLEncoder.encode(template, "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest httpRequest = HttpRequest.get(httpUrl + "?" + stringBuffer);
        HttpResponse execute = httpRequest.execute();
        if (execute.body().equalsIgnoreCase("0")){
            return true;
        }else if (execute.body().equalsIgnoreCase("30")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("40")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("41")){
            log.error("短信发送失败,短信宝账号余额不足");
            return false;
        }else if (execute.body().equalsIgnoreCase("43")){
            log.error("短信发送失败,短信宝IP地址限制");
            return false;
        }else if (execute.body().equalsIgnoreCase("50")){
            log.error("短信发送失败,短信含有敏感词汇");
            return false;
        }else if (execute.body().equalsIgnoreCase("51")){
            log.error("短信发送失败,手机号码输入错误");
            return false;
        }else{
            return false;
        }
    }

    public Boolean sendForgetCode(String phoneNumber,String verifyCode){
        SmsConfig smsConfig = smsConfigService.getSmsConfig();
        if (smsConfig == null){
            log.error("短信发送失败,还未配置发信平台");
            return false;
        }
        if (StrUtil.hasBlank(smsConfig.getSmsPlatform()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPassword()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoUsername()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPrefix())){
            log.error("短信发送失败,请进入后台检查配置是否有缺漏");
            return false;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("u=").append(smsConfig.getDuanxinbaoUsername()).append("&");
        stringBuffer.append("p=").append(SecureUtil.md5(smsConfig.getDuanxinbaoPassword())).append("&");
        stringBuffer.append("m=").append(phoneNumber).append("&");
        String template = String.format(forgetTemplate,smsConfig.getDuanxinbaoPrefix(),verifyCode);
        try {
            stringBuffer.append("c=").append(URLEncoder.encode(template, "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest httpRequest = HttpRequest.get(httpUrl + "?" + stringBuffer);
        HttpResponse execute = httpRequest.execute();
        if (execute.body().equalsIgnoreCase("0")){
            return true;
        }else if (execute.body().equalsIgnoreCase("30")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("40")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("41")){
            log.error("短信发送失败,短信宝账号余额不足");
            return false;
        }else if (execute.body().equalsIgnoreCase("43")){
            log.error("短信发送失败,短信宝IP地址限制");
            return false;
        }else if (execute.body().equalsIgnoreCase("50")){
            log.error("短信发送失败,短信含有敏感词汇");
            return false;
        }else if (execute.body().equalsIgnoreCase("51")){
            log.error("短信发送失败,手机号码输入错误");
            return false;
        }else{
            return false;
        }
    }

    public Boolean sendResetCode(String phoneNumber,String verifyCode){
        SmsConfig smsConfig = smsConfigService.getSmsConfig();
        if (smsConfig == null){
            log.error("短信发送失败,还未配置发信平台");
            return false;
        }
        if (StrUtil.hasBlank(smsConfig.getSmsPlatform()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPassword()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoUsername()) || StrUtil.hasBlank(smsConfig.getDuanxinbaoPrefix())){
            log.error("短信发送失败,请进入后台检查配置是否有缺漏");
            return false;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("u=").append(smsConfig.getDuanxinbaoUsername()).append("&");
        stringBuffer.append("p=").append(SecureUtil.md5(smsConfig.getDuanxinbaoPassword())).append("&");
        stringBuffer.append("m=").append(phoneNumber).append("&");
        String template = String.format(resetTemplate,smsConfig.getDuanxinbaoPrefix(),verifyCode);
        try {
            stringBuffer.append("c=").append(URLEncoder.encode(template, "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpRequest httpRequest = HttpRequest.get(httpUrl + "?" + stringBuffer);
        HttpResponse execute = httpRequest.execute();
        if (execute.body().equalsIgnoreCase("0")){
            return true;
        }else if (execute.body().equalsIgnoreCase("30")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("40")){
            log.error("短信发送失败,短信宝账号密码错误");
            return false;
        }else if (execute.body().equalsIgnoreCase("41")){
            log.error("短信发送失败,短信宝账号余额不足");
            return false;
        }else if (execute.body().equalsIgnoreCase("43")){
            log.error("短信发送失败,短信宝IP地址限制");
            return false;
        }else if (execute.body().equalsIgnoreCase("50")){
            log.error("短信发送失败,短信含有敏感词汇");
            return false;
        }else if (execute.body().equalsIgnoreCase("51")){
            log.error("短信发送失败,手机号码输入错误");
            return false;
        }else{
            return false;
        }
    }

//    public Boolean sendLoginCode(String phoneNumber,String verifyCode){
//
//        if (true){
//            System.out.println(phoneNumber + "登录验证码:" + verifyCode);
//            return true;
//        }
//
//        SmsConfig smsConfig = smsConfigMapper.selectById(1);
//        String platForm = smsConfig.getSmsPlatform();
//        if (StrUtil.hasBlank(platForm)){
//            log.error("未配置短信发送平台");
//            return false;
//        }else{
//            if (platForm.equalsIgnoreCase("aliyun")){
//                return aliyunSmsService.sendLoginCode(phoneNumber,verifyCode);
//            }else if (platForm.equalsIgnoreCase("tencent")){
//                return tencentSmsService.sendLoginCode(phoneNumber,verifyCode);
//            }else{
//                log.error("未知的短信发送平台");
//                return false;
//            }
//        }
//    }
//
//    public Boolean sendRegCode(String phoneNumber,String verifyCode){
//        if (true){
//            System.out.println(phoneNumber + "注册验证码:" + verifyCode);
//            return true;
//        }
//        SmsConfig smsConfig = smsConfigMapper.selectById(1);
//        String platForm = smsConfig.getSmsPlatform();
//        if (StrUtil.hasBlank(platForm)){
//            log.error("未配置短信发送平台");
//            return false;
//        }else{
//            if (platForm.equalsIgnoreCase("aliyun")){
//                return aliyunSmsService.sendRegCode(phoneNumber,verifyCode);
//            }else if (platForm.equalsIgnoreCase("tencent")){
//                return tencentSmsService.sendRegCode(phoneNumber,verifyCode);
//            }else{
//                log.error("未知的短信发送平台");
//                return false;
//            }
//        }
//    }
//
//    public Boolean sendForgetCode(String phoneNumber,String verifyCode){
//        if (true){
//            System.out.println(phoneNumber + "找回验证码:" + verifyCode);
//            return true;
//        }
//        SmsConfig smsConfig = smsConfigMapper.selectById(1);
//        String platForm = smsConfig.getSmsPlatform();
//        if (StrUtil.hasBlank(platForm)){
//            log.error("未配置短信发送平台");
//            return false;
//        }else{
//            if (platForm.equalsIgnoreCase("aliyun")){
//                return aliyunSmsService.sendForgetCode(phoneNumber,verifyCode);
//            }else if (platForm.equalsIgnoreCase("tencent")){
//                return tencentSmsService.sendForgetCode(phoneNumber,verifyCode);
//            }else{
//                log.error("未知的短信发送平台");
//                return false;
//            }
//        }
//    }
//
//    public Boolean sendResetCode(String phoneNumber,String verifyCode){
//        if (true){
//            System.out.println(phoneNumber + "找回验证码:" + verifyCode);
//            return true;
//        }
//        SmsConfig smsConfig = smsConfigMapper.selectById(1);
//        String platForm = smsConfig.getSmsPlatform();
//        if (StrUtil.hasBlank(platForm)){
//            log.error("未配置短信发送平台");
//            return false;
//        }else{
//            if (platForm.equalsIgnoreCase("aliyun")){
//                return aliyunSmsService.sendResetCode(phoneNumber,verifyCode);
//            }else if (platForm.equalsIgnoreCase("tencent")){
//                return tencentSmsService.sendResetCode(phoneNumber,verifyCode);
//            }else{
//                log.error("未知的短信发送平台");
//                return false;
//            }
//        }
//    }
}
