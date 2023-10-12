package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Course;
import com.github.XiaoFeng2233.CheeseEdu.entity.UserCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserCourseMapper extends BaseMapper<UserCourse> {
    @Select("select * from user_course where user_id = #{userId} and course_id = #{courseId}")
    List<UserCourse> selectUserCourseByUserIdAndCourseId(int userId,int courseId);


    @Select("select *,(select title from course where id = user_course.course_id)as course_title," +
            "(select image from course where id = user_course.course_id) as course_image," +
            "(select count(*) from video where course_id = user_course.course_id) as total_video," +
            "(select count(*) from course_records where course_id = user_course.course_id) as learned_video" +
            " from user_course where user_id = #{userId} and status = 1 limit #{count}")
    List<UserCourse> selectCourseByUserIdWithLimit(int userId, int count);

    @Select("select *,(select title from course where id = user_course.course_id)as course_title," +
            "(select image from course where id = user_course.course_id) as course_image," +
            "(select count(*) from video where course_id = user_course.course_id) as total_video," +
            "(select count(*) from course_records where course_id = user_course.course_id) as learned_video" +
            " from user_course where user_id = #{userId} and status = 1")
    List<UserCourse> selectCourseByUserId(int userId);

    @Select("select * from user_course  right join video on user_course.course_id = video.course_id where user_course.user_id = #{userId} and video.course_id = #{courseId} and video.status = 1 and video.id = #{videoId}")
    UserCourse checkUserHasVideoById(int courseId,int userId,int videoId);

    @Select("select * from user_course where user_id = #{userId} and status = 1 and course_id = #{courseId}")
    UserCourse selectCourseByUserIdAndCourseId(int userId,int courseId);
}
