package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.StorageConfig;

import java.net.URL;
import java.util.List;

public interface StorageService {
     Result<URL> getVideoDownloadUrl(StorageConfig storageConfig, String objectName);

     Result<URL> getUploadUrl(StorageConfig storageConfig,String objectName);

     void deleteObject(StorageConfig storageConfig,String objectName);

     void deleteObjects(StorageConfig storageConfig, List<String> objectNames);


}
