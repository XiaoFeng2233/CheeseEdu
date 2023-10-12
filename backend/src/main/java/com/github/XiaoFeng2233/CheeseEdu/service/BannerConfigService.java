package com.github.XiaoFeng2233.CheeseEdu.service;


import com.github.XiaoFeng2233.CheeseEdu.entity.BannerConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import java.util.List;

public interface BannerConfigService {
    Result<List<BannerConfig>> getPcBannerList();
    Result<List<BannerConfig>> getMobileBannerList();
    Result<List<BannerConfig>> getAllBannerList();
    Result<BannerConfig> getBannerById(Integer id);
    Result<String> updateBannerById(BannerConfig bannerConfig);
    Result<String> insertBanner(BannerConfig bannerConfig);
    Result<String> deleteBanner(Integer id);
}
