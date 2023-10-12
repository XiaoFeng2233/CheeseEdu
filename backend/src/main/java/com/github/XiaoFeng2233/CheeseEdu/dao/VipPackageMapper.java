package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.VipPackage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface VipPackageMapper extends BaseMapper<VipPackage> {
}
