package com.github.XiaoFeng2233.CheeseEdu.service;


import com.github.XiaoFeng2233.CheeseEdu.entity.PaymentConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

public interface PaymentConfigService {
    Result<String> updatePaymentConfig(PaymentConfig paymentConfig);
    Result<PaymentConfig> getPaymentConfig();
}
