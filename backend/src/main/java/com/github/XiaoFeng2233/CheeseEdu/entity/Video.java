package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

@Data
@TableName("video")
public class Video implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer courseId;
    private Integer chapterId;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
    private int isDelete;
    private String objectName;
    private int videoDuration;
    private String fileName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deletedTime;
    private int status;
    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String chapterName;
    @TableField(exist = false)
    private URL url;
}
