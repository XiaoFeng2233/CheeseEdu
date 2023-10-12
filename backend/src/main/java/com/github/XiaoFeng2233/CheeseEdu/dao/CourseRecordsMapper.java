package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Mapper
public interface CourseRecordsMapper extends BaseMapper<CourseRecords> {
    @Insert("insert into course_records (user_id,course_id,created_time,video_id) " +
            "select #{userId},#{courseId},#{createdTime},#{videoId} " +
            "from DUAL where not exists (select * from course_records where user_id = #{userId} and course_id = #{courseId} and video_id = #{videoId})")
    int insertWatchRecord(int userId, int courseId, int videoId, Date createdTime);
}
