package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseChapterMapper extends BaseMapper<CourseChapter> {
    @Select("select * from course_chapter where course_id = #{courseId} and is_delete = 0 order by index_number desc, id asc")
    List<CourseChapter> selectChapterByCourseId(int courseId);

    @Select("select * from course_chapter where id = #{id} and is_delete = 0 ")
    CourseChapter selectChapterById(int id);

    @Update("update course_chapter set is_delete = 1 where id = #{id}")
    int deleteChapterById(int id);

}
