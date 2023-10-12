package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.github.XiaoFeng2233.CheeseEdu.dao.SmsConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.SmsConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.SmsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里云和腾讯云审核不通过,已废弃
 */
@Service
@Slf4j
public class AliyunSmsService implements SmsService {

    @Autowired
    private SmsConfigMapper smsConfigMapper;

    @Override
    public Boolean sendLoginCode(String phoneNumber,String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String accessKeyId = smsConfig.getAliyunAccessKeyId();
        String accessKeySecret = smsConfig.getAliyunAccessKeySecret();
        String signName = smsConfig.getAliyunSignName();
        String loginTemplate = smsConfig.getAliyunLoginTemplateId();
        if (StrUtil.hasBlank(accessKeyId,accessKeySecret,signName,loginTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }
        Config config = new Config();
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setPhoneNumbers(phoneNumber);
            sendSmsRequest.setSignName(signName);
            sendSmsRequest.setTemplateCode(verifyCode);
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = client.sendSms(sendSmsRequest);
                System.out.println(sendSmsResponse.getBody());
            }catch (Exception e){
                e.printStackTrace();
                log.error("阿里云发信服务出现错误");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("阿里云发信服务出现未知错误");
        }
        return false;
    }

    @Override
    public Boolean sendRegCode(String phoneNumber,String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String accessKeyId = smsConfig.getAliyunAccessKeyId();
        String accessKeySecret = smsConfig.getAliyunAccessKeySecret();
        String signName = smsConfig.getAliyunSignName();
        String regTemplate = smsConfig.getAliyunRegTemplateId();
        if (StrUtil.hasBlank(accessKeyId,accessKeySecret,signName,regTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }
        Config config = new Config();
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setPhoneNumbers(phoneNumber);
            sendSmsRequest.setSignName(signName);
            sendSmsRequest.setTemplateCode(verifyCode);
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = client.sendSms(sendSmsRequest);
                System.out.println(sendSmsResponse.getBody());
            }catch (Exception e){
                log.error("阿里云发信服务出现错误");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("阿里云发信服务出现未知错误");
        }
        return false;
    }

    @Override
    public Boolean sendForgetCode(String phoneNumber,String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String accessKeyId = smsConfig.getAliyunAccessKeyId();
        String accessKeySecret = smsConfig.getAliyunAccessKeySecret();
        String signName = smsConfig.getAliyunSignName();
        String forgetTemplate = smsConfig.getAliyunForgetTemplateId();
        if (StrUtil.hasBlank(accessKeyId,accessKeySecret,signName,forgetTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }
        Config config = new Config();
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setPhoneNumbers(phoneNumber);
            sendSmsRequest.setSignName(signName);
            sendSmsRequest.setTemplateCode(verifyCode);
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = client.sendSms(sendSmsRequest);
                System.out.println(sendSmsResponse.getBody());
            }catch (Exception e){
                log.error("阿里云发信服务出现错误");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("阿里云发信服务出现未知错误");
        }
        return false;
    }

    @Override
    public Boolean sendResetCode(String phoneNumber, String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String accessKeyId = smsConfig.getAliyunAccessKeyId();
        String accessKeySecret = smsConfig.getAliyunAccessKeySecret();
        String signName = smsConfig.getAliyunSignName();
        String resetTemplate = smsConfig.getAliyunResetTemplateId();
        if (StrUtil.hasBlank(accessKeyId,accessKeySecret,signName,resetTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }
        Config config = new Config();
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setPhoneNumbers(phoneNumber);
            sendSmsRequest.setSignName(signName);
            sendSmsRequest.setTemplateCode(verifyCode);
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = client.sendSms(sendSmsRequest);
                System.out.println(sendSmsResponse.getBody());
            }catch (Exception e){
                log.error("阿里云发信服务出现错误");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("阿里云发信服务出现未知错误");
        }
        return false;
    }
}
