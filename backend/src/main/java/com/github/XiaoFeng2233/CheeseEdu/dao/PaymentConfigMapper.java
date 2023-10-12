package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.PaymentConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PaymentConfigMapper extends BaseMapper<PaymentConfig> {
}
