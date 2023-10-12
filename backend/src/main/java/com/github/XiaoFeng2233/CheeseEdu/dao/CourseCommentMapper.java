package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseCommentMapper extends BaseMapper<CourseComment> {
    @Select("select *,(select avatar from users where id = course_comments.user_id) as avatar , (select nickname from users where id = course_comments.user_id) as nick_name from course_comments where course_id = #{courseId} and is_delete = 0 and status = 1 order by created_time desc")
    List<CourseComment> selectCommentsByCourseId(int courseId);

    @Select("select *,(select nickname from users where id = course_comments.user_id) as nick_name,(select title from course where id = course_comments.course_id) as course_title from course_comments where status = 1 and is_delete = 0")
    IPage<CourseComment> selectCourseComment(Page<?> page);

    @Update("update course_comments set status = 0 ,is_delete = 1,deleted_time = now() where id = #{id}")
    int deleteCourseCommentById(int id);
}
