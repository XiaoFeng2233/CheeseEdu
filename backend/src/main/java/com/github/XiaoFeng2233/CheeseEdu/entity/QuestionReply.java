package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("question_reply")
public class QuestionReply implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer questionId;
    private String images;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deletedTime;
    private Integer userId;
    private Integer status;
    @TableField(exist = false)
    private String userNickName;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private String userName;

}
