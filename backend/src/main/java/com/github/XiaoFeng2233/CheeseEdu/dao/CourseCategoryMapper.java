package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    @Select("select * from course_category where status != 2")
    List<CourseCategory> getAllCourseCategory();

    @Select("select * from course_category where status = 1")
    List<CourseCategory> getAllCourseCategoryForSale();

    @Update("update course_category set delete_time = now(),status = 2 where id = #{id}")
    int deleteCourseCategoryById(int id);


}
