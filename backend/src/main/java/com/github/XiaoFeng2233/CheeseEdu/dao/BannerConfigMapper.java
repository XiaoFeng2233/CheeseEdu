package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.BannerConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BannerConfigMapper extends BaseMapper<BannerConfig> {
    @Select("select * from banner_config order by sort desc")
    List<BannerConfig> getBannerList();

    @Select("select * from banner_config where platform = 'pc' or platform = 'all' order by sort desc")
    List<BannerConfig> getPcBannerList();

    @Select("select * from banner_config where platform = 'mobile' or platform = 'all' order by sort desc")
    List<BannerConfig> getMobileBannerList();

    @Select("select * from banner_config where id = #{id}")
    BannerConfig getBannerById(Integer id);

}
