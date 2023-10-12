package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.StorageConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.StorageService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class TencentStorageService implements StorageService {
    private final long videoExpireTime = 60 * 60 * 1000;
    private final long uploadExpireTime = 10*60*1000;
    private final long uploadExpireTimeWithTwoDays = 2*24*60*60*1000;


    private COSClient getCosClient(StorageConfig storageConfig){
        String secretId = storageConfig.getQcloudSecretId();
        String secretKey = storageConfig.getQcloudSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(storageConfig.getQcloudCosRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    @Override
    public Result<URL> getVideoDownloadUrl(StorageConfig storageConfig,String objName) {
        COSClient cosClient = getCosClient(storageConfig);
        String bucketName = storageConfig.getQcloudBucket();
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, objName, HttpMethodName.GET);
        Date expirationDate = new Date(System.currentTimeMillis() + videoExpireTime);
        req.setExpiration(expirationDate);
        URL url = cosClient.generatePresignedUrl(req);
        cosClient.shutdown();
        if (url != null){
            return new Result<URL>().success(url);
        }else{
            return new Result<URL>().error(null);
        }
    }

    @Override
    public Result<URL> getUploadUrl(StorageConfig storageConfig,String objectName) {
        COSClient cosClient = getCosClient(storageConfig);
        String bucketName = storageConfig.getQcloudBucket();
        Date expirationTime = new Date(System.currentTimeMillis() + uploadExpireTimeWithTwoDays);

        URL url = cosClient.generatePresignedUrl(bucketName, objectName, expirationTime, HttpMethodName.PUT,new HashMap<String,String>(),new HashMap<String,String>());
        cosClient.shutdown();
        if (url != null){
            return new Result<URL>().success(url);
        }else{
            return new Result<URL>().error(null);
        }
    }

    @Override
    public void deleteObject(StorageConfig storageConfig, String objectName) {
        COSClient cosClient = getCosClient(storageConfig);
        String bucketName = storageConfig.getQcloudBucket();
        cosClient.deleteObject(bucketName,objectName);
        cosClient.shutdown();
    }

    @Override
    public void deleteObjects(StorageConfig storageConfig, List<String> objectNames) {
        COSClient cosClient = getCosClient(storageConfig);
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(storageConfig.getQcloudBucket());
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<DeleteObjectsRequest.KeyVersion>();
        Iterator<String> iterator = objectNames.iterator();
        while (iterator.hasNext()){
            keyList.add(new DeleteObjectsRequest.KeyVersion(iterator.next()));
        }
        deleteObjectsRequest.setKeys(keyList);
        cosClient.deleteObjects(deleteObjectsRequest);
        cosClient.shutdown();
    }


}
