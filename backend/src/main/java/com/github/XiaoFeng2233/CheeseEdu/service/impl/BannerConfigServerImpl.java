package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.XiaoFeng2233.CheeseEdu.dao.BannerConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.BannerConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.BannerConfigService;
import com.github.XiaoFeng2233.CheeseEdu.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BannerConfigServerImpl implements BannerConfigService {
    @Autowired
    private BannerConfigMapper bannerConfigMapper;

    @Override
    @Cacheable(value = "banner-config",key = "'PcBannerList'")
    public Result<List<BannerConfig>> getPcBannerList() {
        Result<List<BannerConfig>> result = new Result<>();
        List<BannerConfig> bannerList = bannerConfigMapper.getPcBannerList();
        return result.success(bannerList);
    }

    @Override
    @Cacheable(value = "banner-config",key = "'MobileBannerList'")
    public Result<List<BannerConfig>> getMobileBannerList() {
        Result<List<BannerConfig>> result = new Result<>();
        List<BannerConfig> bannerList = bannerConfigMapper.getMobileBannerList();
        return result.success(bannerList);
    }

    @Override
    @Cacheable(value = "banner-config",key = "'AllBannerList'")
    public Result<List<BannerConfig>> getAllBannerList() {
        Result<List<BannerConfig>> result = new Result<>();
        List<BannerConfig> bannerList = bannerConfigMapper.getBannerList();
        return result.success(bannerList);
    }

    @Override
    public Result<BannerConfig> getBannerById(Integer id) {
        BannerConfig bannerConfig =bannerConfigMapper.getBannerById(id);
        return new Result<BannerConfig>().success(bannerConfig);
    }

    @Override
    @CacheEvict(value = "banner-config",allEntries = true)
    public Result<String> updateBannerById(BannerConfig bannerConfig) {
        System.out.println(bannerConfig);
        if (bannerConfig.getId() == 0 || StrUtil.hasBlank(bannerConfig.getImage(),bannerConfig.getName(),bannerConfig.getPlatform(),bannerConfig.getUrl())){
            return new Result<String>().wrongParams();
        }
        bannerConfig.setUpdatedTime(new Date());
        int i = bannerConfigMapper.updateById(bannerConfig);
        if (i > 0) {
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,后台发生未知错误");
    }

    @Override
    @CacheEvict(value = "banner-config",allEntries = true)
    public Result<String> insertBanner(BannerConfig bannerConfig) {
        System.out.println(bannerConfig);
        if (StrUtil.hasBlank(bannerConfig.getImage(),bannerConfig.getName(),bannerConfig.getPlatform(),bannerConfig.getUrl())){
            return new Result<String>().wrongParams();
        }
        bannerConfig.setCreatedTime(new Date());
        bannerConfig.setUpdatedTime(new Date());
        int i = bannerConfigMapper.insert(bannerConfig);
        if (i>0){
            return new Result<String>().success("添加成功");
        }
        return new Result<String>().error("添加失败,后台发生未知错误");
    }

    @Override
    @CacheEvict(value = "banner-config",allEntries = true)
    public Result<String> deleteBanner(Integer id) {
        int  i = bannerConfigMapper.deleteById(id);
        if (i>0){
            return new Result<String>().success("删除成功");
        }
        return new Result<String>().error("删除失败,后台发生未知错误");
    }
}
