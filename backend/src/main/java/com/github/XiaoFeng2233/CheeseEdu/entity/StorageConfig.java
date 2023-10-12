package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("storage_config")
@Data
public class StorageConfig {
    @TableId(type = IdType.AUTO)
    private  Integer id;
    private String platform;
    private String aliyunAccessKeyId;
    private String aliyunAccessKeySecret;
    private String aliyunBucket;
    private String aliyunEndpoint;
    private String qcloudSecretId;
    private String qcloudSecretKey;
    private String qcloudCosRegion;
    private String qcloudBucket;
}
