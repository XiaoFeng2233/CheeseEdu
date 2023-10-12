package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.PaymentConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.PaymentConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.PaymentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PaymentConfigServiceImpl implements PaymentConfigService {
    @Autowired
    private PaymentConfigMapper paymentConfigMapper;

    @Override
    @CacheEvict(value = "payment-config",allEntries = true)
    public Result<String> updatePaymentConfig(PaymentConfig paymentConfig) {
        paymentConfig.setId(1);
        int i = paymentConfigMapper.updateById(paymentConfig);
        if (i>0){
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,后台发生未知错误");
    }

    @Override
    @Cacheable(cacheNames = "payment-config",key = "'config'")
    public Result<PaymentConfig> getPaymentConfig() {
        PaymentConfig paymentConfig = paymentConfigMapper.selectById(1);
        return new Result<PaymentConfig>().success(paymentConfig);
    }
}
