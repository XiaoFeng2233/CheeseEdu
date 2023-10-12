package com.github.XiaoFeng2233.CheeseEdu.util;

import com.github.XiaoFeng2233.CheeseEdu.entity.PaymentConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.PaymentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayUtil {
    @Autowired
    private PaymentConfigService paymentConfigService;
    public String getPayUrl(){
        Result<PaymentConfig> paymentConfig = paymentConfigService.getPaymentConfig();
        PaymentConfig config = paymentConfig.getData();

        return null;
    }
}
