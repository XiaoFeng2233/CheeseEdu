package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("course_comments")
public class CourseComment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private Integer userId;
    private Integer courseId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deletedTime;
    private Integer isDelete;
    private float star;
    private Integer status;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private String nickName;
    @TableField(exist = false)
    private String courseTitle;
}
