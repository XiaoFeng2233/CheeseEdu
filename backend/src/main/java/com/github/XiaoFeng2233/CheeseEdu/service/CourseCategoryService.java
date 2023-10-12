package com.github.XiaoFeng2233.CheeseEdu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.Course;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseCategory;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import jdk.jfr.Category;

import java.util.List;

public interface CourseCategoryService {

    Result<List<CourseCategory>> getAllCourseCategory();
    Result<CourseCategory> getCourseCategoryById(Integer id);
    Result<String> addCourseCategory(CourseCategory courseCategory);
    Result<String> updateCourseCategory(CourseCategory courseCategory);
    Result<List<CourseCategory>> getAllCourseCategoryForSale();
    Result<String> deleteCourseCategoryById(int id);
}
