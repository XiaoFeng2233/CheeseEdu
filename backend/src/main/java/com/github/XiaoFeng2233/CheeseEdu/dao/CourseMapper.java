package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseMapper extends BaseMapper<Course> {
    @Select("select *,(select name from course_category where id = category_id) as category_name from course where status != '2'")
    List<Course> getAllCourse();

    @Select("select * from course where type = 0 and status = 1 order by created_time desc limit 12")
    List<Course> getFreeCourse();

    @Select("select * from course where type != 0 and status = 1 order by created_time desc limit 12")
    List<Course> getLatestCourse();

    IPage<Course> getCourseByCondition(Page<Course> page, @Param("categoryId") Integer categoryId , @Param("sort")Integer sort );

    @Select("select * from course where status = 1 order by rand() limit #{number}")
    List<Course> getRandomCourse(int number);

    @Select("select *,(select count(*) from video where course_id = course.id and status = 1 and is_delete = 0) as video_count from course where id = #{id} and status != 2")
    Course getCourseById(int id);

    @Select("select *,(select count(*) from video where course_id = course.id and status = 1 and is_delete = 0) as video_count from course where id = #{id} and status = 1")
    Course getActiveCourseById(int id);

    @Select("select * from course where title like CONCAT('%',#{keyword},'%')  or short_description like CONCAT('%',#{keyword},'%') and status = 1 ")
    List<Course> searchMapper(String keyword);

    @Update("update course set status = 2 , deleted_time = now() where id = #{id}")
    int deleteCourseById(int id);

    @Select("select * from course where category_id = #{id}")
    List<Course> selectCourseByCategoryId(int id);

    @Update("UPDATE course set student_number = IFNULL(student_number,0)+1 WHERE id = #{id}")
    int increasingStudentNumber(int id);


}
