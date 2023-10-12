package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.dao.*;

import com.github.XiaoFeng2233.CheeseEdu.entity.*;

import com.github.XiaoFeng2233.CheeseEdu.service.CourseChapterService;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseRecordsService;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseService;
import com.github.XiaoFeng2233.CheeseEdu.util.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserCourseMapper userCourseMapper;
    @Autowired
    private CourseChapterMapper courseChapterMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private StorageUtil storageUtil;
    @Autowired
    private CourseChapterService courseChapterService;
    @Autowired
    private CourseRecordsService courseRecordsService;

    @Override
    public Result<List<Course>> getAllCourse() {
        List<Course> allCourse = courseMapper.getAllCourse();
        return new Result<List<Course>>().success(allCourse);
    }

    @Override
    @CacheEvict(value = "course", allEntries = true)
    public Result<String> addCourse(Course course) {
        int type = course.getType();
        if (type != 0) {
            if (course.getPrice() == null) {
                return new Result<>().wrongParams();
            }
            if (course.getPrice().compareTo(new BigDecimal(0)) != 1) {
                return new Result<>().wrongParams();
            }
        } else {
            course.setPrice(new BigDecimal(0));
        }

        course.setPrice(course.getPrice().setScale(2, RoundingMode.DOWN));
        course.setStudentNumber(0);
        course.setCreatedTime(new Date());
        course.setUpdatedTime(new Date());
        int i = courseMapper.insert(course);
        if (i > 0) {
            return new Result<String>().success("课程添加成功");
        } else {
            return new Result<String>().error("添加失败,后台发生未知错误");
        }
    }

    @Override
    @Cacheable(cacheNames = "course", key = "'freecourse'")
    public Result<List<Course>> getFreeCourse() {
        List<Course> freeCourse = courseMapper.getFreeCourse();
        return new Result<List<Course>>().success(freeCourse);
    }

    @Override
    @Cacheable(cacheNames = "course", key = "'latestcourse'")
    public Result<List<Course>> getLatestCourse() {
        List<Course> latestCourse = courseMapper.getLatestCourse();
        return new Result<List<Course>>().success(latestCourse);
    }

    @Override
    public Result<IPage<Course>> getCourseByCondition(int page, int categoryId, int sort) {
        Page<Course> p = new Page<>(page, 20);
        IPage<Course> ipage = courseMapper.getCourseByCondition(p, categoryId, sort);
        return new Result<IPage<Course>>().success(ipage);
    }

    @Override
    public Result<List<Course>> getRandomCourse(int number) {
        List<Course> list = courseMapper.getRandomCourse(number);
        return new Result<List<Course>>().success(list);
    }

    @Override
    public Result<Course> getCourseById(int id) {
        Course course = courseMapper.getCourseById(id);
        return new Result<Course>().success(course);
    }



    @Override
    @CacheEvict(value = "course", allEntries = true)
    public Result<String> updateCourse(Course course) {
        int i = courseMapper.updateById(course);
        if (i>0){
            return new Result<String>().success("更新成功");
        }else{
            return new Result<String>().error("更新失败,发生未知错误");
        }
    }

    @Override
    public Result<Course> getActiveCourseById(int id) {
        Course course = courseMapper.getActiveCourseById(id);
        return new Result<Course>().success(course);
    }

    @Override
    public Result<List<UserCourse>> getMyCourse(int userId, int count) {
        if (count==0){
            List<UserCourse> courses = userCourseMapper.selectCourseByUserId(userId);
            return new Result<List<UserCourse>>().success(courses);
        }else{
            List<UserCourse> courses = userCourseMapper.selectCourseByUserIdWithLimit(userId, count);
            return new Result<List<UserCourse>>().success(courses);
        }
    }


    @Override
    public Result<Object> getCourseVideoByCourseId(int courseId, int userId) {
        UserCourse userCourses = userCourseMapper.selectCourseByUserIdAndCourseId(userId,courseId);
        if (userCourses != null){
            List<CourseChapter> courseChapters = courseChapterMapper.selectChapterByCourseId(courseId);
            List<Video> videos = videoMapper.selectVideosByCourseId(courseId);
            courseChapters.forEach(e->{
                ArrayList<Video> videoArrayList = new ArrayList<>();
                videos.forEach(b->{
                    if (b.getChapterId().equals(e.getId())){
                        videoArrayList.add(b);
                    }
                });
                e.setVideoList(videoArrayList);
            });
            return new Result<Object>().success(courseChapters);
        }else{
            return new Result<Object>().error("你还未拥有该课程");
        }
    }

    @Override
    public Result<URL> getVideoPlayUrl(int courseId, int userId, int videoId) {
        UserCourse userCourse = userCourseMapper.checkUserHasVideoById(courseId,userId,videoId);
        if(userCourse == null){

            return new Result<URL>().error(null);
        }else{
            Video video = videoMapper.selectVideoById(videoId);
            CourseRecords courseRecord = new CourseRecords();
            courseRecord.setCourseId(courseId);
            courseRecord.setUserId(userId);
            courseRecord.setVideoId(videoId);
            courseRecord.setCreatedTime(new Date());
            courseRecordsService.insertWatchRecord(courseRecord);
            return storageUtil.getVideoPlayUrl(video.getObjectName());
        }
    }

    @Override
    public Result<List<CourseChapter>> getCourseChapter(int courseId) {
        List<CourseChapter> courseChapters = courseChapterMapper.selectChapterByCourseId(courseId);
        List<Video> videos = videoMapper.selectVideosByCourseId(courseId);
        courseChapters.forEach(e->{
            ArrayList<Video> videoArrayList = new ArrayList<>();
            videos.forEach(b->{
                if (b.getChapterId().equals(e.getId())){
                    videoArrayList.add(b);
                }
            });
            e.setVideoList(videoArrayList);
        });
        return new Result<List<CourseChapter>>().success(courseChapters);
    }

    @Override
    public List<Course> searchCourse(String keyword) {
        return courseMapper.searchMapper(keyword);
    }

    @Override
    public Result<String> deleteCourseById(int id) {
        Result<List<CourseChapter>> chapterByCourseId = courseChapterService.getChapterByCourseId(id);
        List<CourseChapter> data = chapterByCourseId.getData();
        if (data != null && data.size()>0){
            return new Result<String>().error("删除失败,该课程目录下存在未删除的章节");
        }else{
            courseMapper.deleteCourseById(id);
            return new Result<String>().success("课程删除成功");
        }
    }

    @Override
    public Result<List<Course>> getCourseByCategoryId(int id) {
        List<Course> courses = courseMapper.selectCourseByCategoryId(id);
        return new Result<List<Course>>().success(courses);
    }

    @Override
    public int increasingStudentNumberById(int id) {
        return courseMapper.increasingStudentNumber(id);
    }
}
