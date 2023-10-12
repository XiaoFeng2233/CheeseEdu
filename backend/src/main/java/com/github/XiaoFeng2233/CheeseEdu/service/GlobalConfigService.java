package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.GlobalConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import java.util.HashMap;

public interface GlobalConfigService {
    Result<HashMap<String,Object>> getBaseConfig();
    Result<GlobalConfig> getConfig();
    Result<String> updateGlobalConfig(GlobalConfig globalConfig);
}
