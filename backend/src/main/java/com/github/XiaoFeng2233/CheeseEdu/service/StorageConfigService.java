package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.StorageConfig;

public interface StorageConfigService {
    Result<StorageConfig> getStorageConfig();
    Result<String> updateStorageConfig(StorageConfig storageConfig);
}
