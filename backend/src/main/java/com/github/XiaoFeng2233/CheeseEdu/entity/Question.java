package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("question")
@Data
public class Question implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String title;
    private Integer status;
    private String images;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deletedTime;
    private String content;
    private Integer isSolved;
    private Integer viewCount;
    @TableField(exist = false)
    private String userAvatar;
    @TableField(exist = false)
    private String userNickName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private int answerCount;
}
