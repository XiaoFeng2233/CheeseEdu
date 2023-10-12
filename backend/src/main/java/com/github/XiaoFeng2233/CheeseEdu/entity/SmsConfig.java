package com.github.XiaoFeng2233.CheeseEdu.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sms_config")
public class SmsConfig implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String smsPlatform;
    private String aliyunAccessKeyId;
    private String aliyunAccessKeySecret;
    private String aliyunSignName;
    private String aliyunRegTemplateId;
    private String aliyunLoginTemplateId;
    private String aliyunForgetTemplateId;
    private String tencentSecretId;
    private String tencentSecretKey;
    private String tencentSdkAppId;
    private String tencentRegTemplateId;
    private String tencentLoginTemplateId;
    private String tencentForgetTemplateId;
    private String tencentSignName;
    private String tencentRegion;
    private String tencentResetTemplateId;
    private String aliyunResetTemplateId;
    private String duanxinbaoUsername;
    private String duanxinbaoPassword;
    private String duanxinbaoPrefix;
}
