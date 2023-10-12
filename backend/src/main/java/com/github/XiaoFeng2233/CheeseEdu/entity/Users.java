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

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/7/10 20:01
 */
@Data
@TableName("users")
public class Users implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String salt;
    private String mobileNumber;
    private String avatar;

    /**
     * 0代表不是   1代表是
     */
    private Integer isLock;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatedTime;

    private String regArea;
    private String regIp;
    /**
     * 0代表不是   1代表是
     */
    private Integer isAdmin;

    /**
     * 0代表封号/冻结账户   1代表正常
     */
    private Integer status;

    private Integer isVip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date vipExpiredTime;

    private Integer vipPackageId;

    @TableField(exist = false)
    private String vipPackageName;

}
