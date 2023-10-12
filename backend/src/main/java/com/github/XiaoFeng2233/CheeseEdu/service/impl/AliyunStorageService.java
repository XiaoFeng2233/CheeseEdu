package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.StorageConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.StorageService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class AliyunStorageService implements StorageService {
    private final long videoExpireTime = 60 * 60 * 1000;
    private final long uploadExpireTime = 10*60*1000;
    private final long uploadExpireTimeWithTwoDays = 2*24*60*60*1000;

    private OSS getOssClient(StorageConfig storageConfig){
        String endpoint = storageConfig.getAliyunEndpoint();
        String accessKeyId = storageConfig.getAliyunAccessKeyId();
        String accessKeySecret = storageConfig.getAliyunAccessKeySecret();
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        return ossClient;
    }

    @Override
    public Result<URL> getVideoDownloadUrl(StorageConfig storageConfig, String objectName) {
        OSS ossClient = getOssClient(storageConfig);
        String bucketName = storageConfig.getAliyunBucket();
        Date expiration = new Date(new Date().getTime() + videoExpireTime);
        URL url = ossClient.generatePresignedUrl(bucketName,objectName,expiration);
        ossClient.shutdown();
        if (url != null){
            return new Result<URL>().success(url);
        }else{
            return new Result<URL>().error(null);
        }

    }

    @Override
    public Result<URL> getUploadUrl(StorageConfig storageConfig,String objectName) {
        OSS ossClient = getOssClient(storageConfig);
        String bucketName = storageConfig.getAliyunBucket();
        Date expiration = new Date(new Date().getTime() + uploadExpireTimeWithTwoDays);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName,objectName, HttpMethod.PUT);
        request.setExpiration(expiration);
        URL uploadUrl = ossClient.generatePresignedUrl(request);
        ossClient.shutdown();
        if (uploadUrl != null){
            return new Result<URL>().success(uploadUrl);
        }else{
            return new Result<URL>().error(null);
        }
    }

    @Override
    public void deleteObject(StorageConfig storageConfig, String objectName) {
        OSS ossClient = getOssClient(storageConfig);
        String bucketName = storageConfig.getAliyunBucket();
        ossClient.deleteObject(bucketName,objectName);
        ossClient.shutdown();
    }

    @Override
    public void deleteObjects(StorageConfig storageConfig, List<String> objectNames) {
        OSS ossClient = getOssClient(storageConfig);
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(storageConfig.getAliyunBucket());
        deleteObjectsRequest.withKeys(objectNames);
        ossClient.deleteObjects(deleteObjectsRequest);
        ossClient.shutdown();
    }

}
