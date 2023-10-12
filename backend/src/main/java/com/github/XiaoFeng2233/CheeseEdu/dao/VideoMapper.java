package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Video;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface VideoMapper extends BaseMapper<Video> {
    @Select("select *,(select title from course where id = course_id) as course_name,(select name from course_chapter where id = chapter_id) as chapter_name from video where course_id = #{courseId} and status = 1 and is_delete = 0 order by id asc")
    List<Video> selectVideosByCourseId(int courseId);

    @Update("update video set title = #{title},chapter_id = #{chapterId},updated_time = #{updatedTime} where id = #{id} and is_delete = 0 and status = 1")
    int updateVideoById(int id, String title, int chapterId, Date updatedTime);

    @Update("update video set status = 1 where object_name = #{name} and is_delete = 0 and status = 0")
    int updateStatusByObjectName(@Param("name") String name);

    @Select("select * from video where id = #{id}")
    Video selectVideoById(int id);

    @Select("select * from video where chapter_id = #{chapterId} and status = 1 and is_delete = 0")
    List<Video> selectVideoByChapterId(int chapterId);

    @Update("update video set status = 0 ,is_delete = 1,deleted_time = now()  where id = #{id}")
    int deleteVideoById(int id);
}
