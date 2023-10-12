package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.XiaoFeng2233.CheeseEdu.dao.SmsConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.SmsConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.SmsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里云和腾讯云审核不通过,已废弃
 */
@Service
@Slf4j
public class TencentSmsService implements SmsService {

    @Autowired
    private SmsConfigMapper smsConfigMapper;

    @Override
    public Boolean sendLoginCode(String phoneNumber,String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String secretKey = smsConfig.getTencentSecretKey();
        String secretId = smsConfig.getTencentSecretId();
        String sdkAppId = smsConfig.getTencentSdkAppId();
        String signName = smsConfig.getTencentSignName();
        String region = smsConfig.getTencentRegion();
        String loginTemplate = smsConfig.getTencentLoginTemplateId();
        if (StrUtil.hasBlank(secretKey,secretId,sdkAppId,signName,region,loginTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }else{
            Credential credential = new Credential(secretId,secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient smsClient = new SmsClient(credential,region,clientProfile);
            SendSmsRequest request = new SendSmsRequest();
            String[] numbers = {"+86" + phoneNumber};
            request.setPhoneNumberSet(numbers);
            request.setSmsSdkAppId(sdkAppId);
            request.setSignName(signName);
            request.setTemplateId(loginTemplate);
            String[] codes = {verifyCode};
            request.setTemplateParamSet(codes);
            SendSmsResponse response = null;
            try {
                response = smsClient.SendSms(request);
                String responseContent = SendSmsResponse.toJsonString(response);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseContent, JsonObject.class);
                String code = jsonObject.get("SendStatusSet").getAsJsonArray().get(0).getAsJsonObject().get("Code").toString();
                if (code.equalsIgnoreCase("Ok")){
                    return true;
                } else{
                    log.error(responseContent);
                    log.error("腾讯云发信服务出现错误");
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("腾讯云发信服务出现未知错误");
                return false;
            }
        }
    }

    @Override
    public Boolean sendRegCode(String phoneNumber,String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String secretKey = smsConfig.getTencentSecretKey();
        String secretId = smsConfig.getTencentSecretId();
        String sdkAppId = smsConfig.getTencentSdkAppId();
        String signName = smsConfig.getTencentSignName();
        String region = smsConfig.getTencentRegion();
        String regTemplate = smsConfig.getTencentRegTemplateId();
        if (StrUtil.hasBlank(secretKey,secretId,sdkAppId,signName,region,regTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }else{
            Credential credential = new Credential(secretId,secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient smsClient = new SmsClient(credential,region,clientProfile);
            SendSmsRequest request = new SendSmsRequest();
            String[] numbers = {"+86" + phoneNumber};
            request.setPhoneNumberSet(numbers);
            request.setSmsSdkAppId(sdkAppId);
            request.setSignName(signName);
            request.setTemplateId(regTemplate);
            String[] codes = {verifyCode};
            request.setTemplateParamSet(codes);
            SendSmsResponse response = null;
            try {
                response = smsClient.SendSms(request);
                String responseContent = SendSmsResponse.toJsonString(response);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseContent, JsonObject.class);
                String code = jsonObject.get("SendStatusSet").getAsJsonArray().get(0).getAsJsonObject().get("Code").toString();
                if (code.equalsIgnoreCase("Ok")){
                    return true;
                } else{
                    log.error(responseContent);
                    log.error("腾讯云发信服务出现错误");
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("腾讯云发信服务出现未知错误");
                return false;
            }
        }
    }

    @Override
    public Boolean sendForgetCode(String phoneNumber,String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String secretKey = smsConfig.getTencentSecretKey();
        String secretId = smsConfig.getTencentSecretId();
        String sdkAppId = smsConfig.getTencentSdkAppId();
        String signName = smsConfig.getTencentSignName();
        String region = smsConfig.getTencentRegion();
        String forgetTemplate = smsConfig.getTencentForgetTemplateId();
        if (StrUtil.hasBlank(secretKey,secretId,sdkAppId,signName,region,forgetTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }else{
            Credential credential = new Credential(secretId,secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient smsClient = new SmsClient(credential,region,clientProfile);
            SendSmsRequest request = new SendSmsRequest();
            String[] numbers = {"+86" + phoneNumber};
            request.setPhoneNumberSet(numbers);
            request.setSmsSdkAppId(sdkAppId);
            request.setSignName(signName);
            request.setTemplateId(forgetTemplate);
            String[] codes = {verifyCode};
            request.setTemplateParamSet(codes);
            SendSmsResponse response = null;
            try {
                response = smsClient.SendSms(request);
                String responseContent = SendSmsResponse.toJsonString(response);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseContent, JsonObject.class);
                String code = jsonObject.get("SendStatusSet").getAsJsonArray().get(0).getAsJsonObject().get("Code").toString();
                if (code.equalsIgnoreCase("Ok")){
                    return true;
                } else{
                    log.error(responseContent);
                    log.error("腾讯云发信服务出现错误");
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("腾讯云发信服务出现未知错误");
                return false;
            }
        }
    }

    @Override
    public Boolean sendResetCode(String phoneNumber, String verifyCode) {
        SmsConfig smsConfig = smsConfigMapper.selectById(1);
        String secretKey = smsConfig.getTencentSecretKey();
        String secretId = smsConfig.getTencentSecretId();
        String sdkAppId = smsConfig.getTencentSdkAppId();
        String signName = smsConfig.getTencentSignName();
        String region = smsConfig.getTencentRegion();
        String resetTemplate = smsConfig.getTencentResetTemplateId();
        if (StrUtil.hasBlank(secretKey,secretId,sdkAppId,signName,region,resetTemplate)){
            log.error("短信平台配置存在空值");
            return false;
        }else{
            Credential credential = new Credential(secretId,secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient smsClient = new SmsClient(credential,region,clientProfile);
            SendSmsRequest request = new SendSmsRequest();
            String[] numbers = {"+86" + phoneNumber};
            request.setPhoneNumberSet(numbers);
            request.setSmsSdkAppId(sdkAppId);
            request.setSignName(signName);
            request.setTemplateId(resetTemplate);
            String[] codes = {verifyCode};
            request.setTemplateParamSet(codes);
            SendSmsResponse response = null;
            try {
                response = smsClient.SendSms(request);
                String responseContent = SendSmsResponse.toJsonString(response);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseContent, JsonObject.class);
                String code = jsonObject.get("SendStatusSet").getAsJsonArray().get(0).getAsJsonObject().get("Code").toString();
                if (code.equalsIgnoreCase("Ok")){
                    return true;
                } else{
                    log.error(responseContent);
                    log.error("腾讯云发信服务出现错误");
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("腾讯云发信服务出现未知错误");
                return false;
            }
        }
    }
}
