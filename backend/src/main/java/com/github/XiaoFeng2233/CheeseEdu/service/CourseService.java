package com.github.XiaoFeng2233.CheeseEdu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

public interface CourseService {

    Result<List<Course>> getAllCourse();

    Result<String> addCourse(Course course);

    Result<List<Course>> getFreeCourse();

    Result<List<Course>> getLatestCourse();

    Result<IPage<Course>> getCourseByCondition(int page, int categoryId, int sort);

    Result<List<Course>> getRandomCourse(int number);

    Result<Course> getCourseById(int id);

    Result<String> updateCourse(Course course);

    Result<Course> getActiveCourseById(int id);

    Result<List<UserCourse>> getMyCourse(int userId, int count);

    Result<Object> getCourseVideoByCourseId(int courseId, int userId);

    Result<URL> getVideoPlayUrl(int courseId, int userId, int videoId);

    Result<List<CourseChapter>> getCourseChapter(int courseId);

    List<Course> searchCourse(String keyword);

    Result<String> deleteCourseById(int id);

    Result<List<Course>> getCourseByCategoryId(int id);

    int increasingStudentNumberById(int id);
}
