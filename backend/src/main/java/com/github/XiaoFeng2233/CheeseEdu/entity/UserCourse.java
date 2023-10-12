package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("user_course")
@Data
public class UserCourse {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer courseId;
    private BigDecimal price;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    private Integer status;
    private int isFree;
    @TableField(exist = false)
    private String courseTitle;
    @TableField(exist = false)
    private String courseImage;
    @TableField(exist = false)
    private int totalVideo;
    @TableField(exist = false)
    private int learnedVideo;

}
