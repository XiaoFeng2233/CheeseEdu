package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.GlobalConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.GlobalConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.GlobalConfigService;
import com.github.XiaoFeng2233.CheeseEdu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {
    @Autowired
    private GlobalConfigMapper globalConfigMapper;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    @Cacheable(cacheNames ="global-config",key = "'BaseConfig'")
    public Result<HashMap<String, Object>> getBaseConfig() {
        HashMap<String,Object> map = new HashMap<>();
        GlobalConfig config = globalConfigMapper.selectById(1);
        map.put("siteName",config.getSiteName());
        map.put("siteIcon",config.getSiteIcon());
        map.put("siteFooter",config.getSiteFooter());
        map.put("siteDescription",config.getSiteDescription());
        map.put("siteLogo",config.getSiteLogo());
        map.put("siteKeyword",config.getSiteKeyword());
        return new Result<HashMap<String,Object>>().success(map);
    }

    @Override
    public Result<GlobalConfig> getConfig() {
        GlobalConfig config = globalConfigMapper.selectById(1);
        return new Result<GlobalConfig>().success(config);
    }

    @Override
    @CacheEvict(cacheNames ="global-config",key = "'BaseConfig'")
    public Result<String> updateGlobalConfig(GlobalConfig globalConfig) {
        int i = globalConfigMapper.updateById(globalConfig);
        if (i>0){
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,服务器发生未知错误");
    }
}
