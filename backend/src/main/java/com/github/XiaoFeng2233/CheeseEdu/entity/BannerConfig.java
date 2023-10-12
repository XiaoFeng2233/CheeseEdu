package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/8/3 22:43
 */
@Data
@TableName("banner_config")
public class BannerConfig implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String url;
    private String name;
    private String image;
    private Date createdTime;
    private Date updatedTime;
    private String platform;
    private Integer sort;

}
