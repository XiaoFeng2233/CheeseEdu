package com.github.XiaoFeng2233.CheeseEdu.util;

import com.github.XiaoFeng2233.CheeseEdu.dao.StorageConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.StorageConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.StorageConfigService;
import com.github.XiaoFeng2233.CheeseEdu.service.StorageService;
import com.github.XiaoFeng2233.CheeseEdu.service.impl.AliyunStorageService;
import com.github.XiaoFeng2233.CheeseEdu.service.impl.TencentStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@Slf4j
public class StorageUtil {
    @Autowired
    private StorageConfigService storageConfigService;
    @Autowired
    private AliyunStorageService aliyunStorageService;
    @Autowired
    private TencentStorageService tencentStorageService;

    public Result<URL> getVideoPlayUrl(String objName) {
        StorageConfig storageConfig = storageConfigService.getStorageConfig().getData();
        if (storageConfig == null) {
            log.error("未获取到存储平台配置");
        }else{
            if (storageConfig.getPlatform().equalsIgnoreCase("qcloud")) {
                Result<URL> videoDownloadUrl = tencentStorageService.getVideoDownloadUrl(storageConfig, objName);
                return videoDownloadUrl;
            } else if (storageConfig.getPlatform().equalsIgnoreCase("aliyun")) {
                Result<URL> videoDownloadUrl = aliyunStorageService.getVideoDownloadUrl(storageConfig,objName);
                return videoDownloadUrl;
            } else {
                log.error("数据库存储配置出现异常,发现未匹配存储平台");
            }
        }
        return null;
    }

    public Result<URL> getVideoUploadUrl(String objName) {
        StorageConfig storageConfig = storageConfigService.getStorageConfig().getData();
        if (storageConfig == null) {
            log.error("未获取到存储平台配置");
        }
        if (storageConfig.getPlatform().equalsIgnoreCase("qcloud")) {
            return tencentStorageService.getUploadUrl(storageConfig, objName);
        } else if (storageConfig.getPlatform().equalsIgnoreCase("aliyun")) {
            return aliyunStorageService.getUploadUrl(storageConfig, objName);

        } else {
            log.error("数据库存储配置出现异常,发现未匹配存储平台");
        }
        return null;
    }

    public void deleteObject(String objectName) {
        StorageConfig storageConfig = storageConfigService.getStorageConfig().getData();
        if (storageConfig == null) {
            log.error("未获取到存储平台配置");
        }
        if (storageConfig.getPlatform().equalsIgnoreCase("qcloud")) {
            tencentStorageService.deleteObject(storageConfig, objectName);
        } else if (storageConfig.getPlatform().equalsIgnoreCase("aliyun")) {
            aliyunStorageService.deleteObject(storageConfig, objectName);
        } else {
            log.error("数据库存储配置出现异常,发现未匹配存储平台");
        }

    }

    public void deleteObjects(List<String> objectNames) {
        StorageConfig storageConfig = storageConfigService.getStorageConfig().getData();
        if (storageConfig == null) {
            log.error("未获取到存储平台配置");
        }
        if (storageConfig.getPlatform().equalsIgnoreCase("qcloud")) {
            tencentStorageService.deleteObjects(storageConfig, objectNames);
        } else if (storageConfig.getPlatform().equalsIgnoreCase("aliyun")) {
            aliyunStorageService.deleteObjects(storageConfig, objectNames);
        } else {
            log.error("数据库存储配置出现异常,发现未匹配存储平台");
        }
    }

}
