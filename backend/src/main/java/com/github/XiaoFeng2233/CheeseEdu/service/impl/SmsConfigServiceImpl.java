package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.SmsConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.SmsConfig;
import com.github.XiaoFeng2233.CheeseEdu.service.SmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsConfigServiceImpl implements SmsConfigService {
    @Autowired
    private SmsConfigMapper smsConfigMapper;

    @Override
    public int updateSmsConfig(SmsConfig smsConfig) {
        smsConfig.setId(1);
        return smsConfigMapper.updateById(smsConfig);
    }

    @Override
    public SmsConfig getSmsConfig() {
        return smsConfigMapper.selectById(1);
    }
}
