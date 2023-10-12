package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.StorageConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.StorageConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.StorageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StorageConfigServiceImpl implements StorageConfigService {

    @Autowired
    private StorageConfigMapper storageConfigMapper;

    @Override
    @Cacheable(value = "storage-config",key = "'config'")
    public Result<StorageConfig> getStorageConfig() {
        StorageConfig storageConfig = storageConfigMapper.selectById(1);
        Result<StorageConfig> result = new Result<>();
        if (storageConfig == null){
            return result.success(null);
        }
        return result.success(storageConfig);
    }

    @Override
    @CacheEvict(value = "storage-config",allEntries = true)
    public Result<String> updateStorageConfig(StorageConfig storageConfig) {
        storageConfig.setId(1);
        int i = storageConfigMapper.updateById(storageConfig);
        if (i>0){
            return new Result<String>().success("保存成功");
        }else{
            return new Result<String>().error("保存失败,发生未知错误");
        }
    }
}
