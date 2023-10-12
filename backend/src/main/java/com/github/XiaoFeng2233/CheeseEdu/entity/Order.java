package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_order")
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer courseId;
    private Integer vipPackageId;
    private Integer type;
    @TableField(value = "user_id")
    private Integer userId;
    private BigDecimal price;
    private Integer isFree;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
    private Integer isPayOk;
    private Integer status;
    private String orderNum;
    private String payment;
    private String paymentNum;
    private String orderName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expireTime;
    @TableField(exist = false)
    private String courseImage;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String courseTitle;
    @TableField(exist = false)
    private String vipPackageName;
}
