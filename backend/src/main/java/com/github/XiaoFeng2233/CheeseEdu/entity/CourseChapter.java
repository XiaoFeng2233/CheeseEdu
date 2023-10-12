package com.github.XiaoFeng2233.CheeseEdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("course_chapter")
public class CourseChapter implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer courseId;
    private Integer indexNumber;
    @TableField(exist = false)
    private String courseName;
    private int isDelete;
    @TableField(exist = false)
    private List<Video> videoList;
}
