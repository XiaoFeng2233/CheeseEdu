package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.CourseCategoryMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Course;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseCategory;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseCategoryService;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Autowired
    private CourseService courseService;

    @Override
    @Cacheable(cacheNames = "course-category",key = "'AllCourseCategory'")
    public Result<List<CourseCategory>> getAllCourseCategory() {
        List<CourseCategory> list = courseCategoryMapper.getAllCourseCategory();
        return new Result<List<CourseCategory>>().success(list);
    }

    @Override
    public Result<CourseCategory> getCourseCategoryById(Integer id) {
        CourseCategory courseCategory = courseCategoryMapper.selectById(id);
        return new Result<CourseCategory>().success(courseCategory);
    }

    @Override
    @CacheEvict(value = "course-category" , allEntries = true)
    public Result<String> addCourseCategory(CourseCategory courseCategory) {
        courseCategory.setCreatedTime(new Date());
        courseCategory.setUpdatedTime(new Date());
        int i = courseCategoryMapper.insert(courseCategory);
        if (i>0){
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,后台发生未知错误");
    }

    @Override
    @CacheEvict(value = "course-category" , allEntries = true)
    public Result<String> updateCourseCategory(CourseCategory courseCategory) {
        int i = courseCategoryMapper.updateById(courseCategory);
        if (i>0){
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,后台发生未知错误");
    }

    @Override
    @Cacheable(cacheNames = "course-category",key = "'AllCourseCategoryForSale'")
    public Result<List<CourseCategory>> getAllCourseCategoryForSale() {
        List<CourseCategory> list = courseCategoryMapper.getAllCourseCategoryForSale();
        return new Result<List<CourseCategory>>().success(list);    }

    @Override
    @CacheEvict(value = "course-category" , allEntries = true)
    public Result<String> deleteCourseCategoryById(int id) {
        Result<List<Course>> courseByCategoryId = courseService.getCourseByCategoryId(id);
        List<Course> data = courseByCategoryId.getData();
        if (data  != null && data.size()> 0 ){
            return new Result<String>().error("删除失败,该分类下存在未删除的课程");
        }else{
            courseCategoryMapper.deleteCourseCategoryById(id);
            return new Result<String>().success("分类删除成功");
        }
    }
}
