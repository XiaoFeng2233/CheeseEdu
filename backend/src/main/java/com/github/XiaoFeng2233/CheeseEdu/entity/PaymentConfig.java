package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("payment_config")
public class PaymentConfig implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String alipayPublicKey;
    private String notifyUrl;
    private String merchantPrivateKey;
    private String appId;
}
