package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("global_config")
public class GlobalConfig implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String siteName;
    private String siteIcon;
    private String siteAgreement;
    private String siteFooter;
    private String siteDescription;
    private String siteKeyword;
    private String defaultAvatar;
    private String siteLogo;
}
