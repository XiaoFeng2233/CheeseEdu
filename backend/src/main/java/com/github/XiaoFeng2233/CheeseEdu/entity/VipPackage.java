package com.github.XiaoFeng2233.CheeseEdu.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("vip_package")
@Data
public class VipPackage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer duration;
    private BigDecimal price;
}
