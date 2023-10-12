package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.CourseChapterMapper;
import com.github.XiaoFeng2233.CheeseEdu.dao.VideoMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseChapter;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Video;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseChapterService;
import com.github.XiaoFeng2233.CheeseEdu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseChapterServiceImpl implements CourseChapterService {
    @Autowired
    private CourseChapterMapper courseChapterMapper;
    @Autowired
    private VideoService videoService;


    @Override
    public Result<List<CourseChapter>> getChapterByCourseId(int id) {
        List<CourseChapter> courseChapters = courseChapterMapper.selectChapterByCourseId(id);
        return new Result<List<CourseChapter>>().success(courseChapters);
    }

    @Override
    public Result<String> addChapter(CourseChapter courseChapter) {
        courseChapter.setIsDelete(0);
        int i = courseChapterMapper.insert(courseChapter);
        if (i > 0) {
            return new Result<String>().success("添加成功");
        }
        return new Result<String>().error("添加失败,发生未知错误");
    }

    @Override
    public Result<CourseChapter> getChapterById(int id) {
        CourseChapter courseChapter = courseChapterMapper.selectChapterById(id);
        return new Result<CourseChapter>().success(courseChapter);
    }

    @Override
    public Result<String> updateChapterById(CourseChapter courseChapter) {
        int i = courseChapterMapper.updateById(courseChapter);
        if (i > 0) {
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,发生未知错误");
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    public Result<String> deleteChapterById(int id) {
        Result<List<Video>> videoByChapterId = videoService.getVideoByChapterId(id);
        List<Video> data = videoByChapterId.getData();
        if (data != null && data.size()>0){
            return new Result<String>().error("删除失败,该章节下存在未删除的视频");
        }else{
            courseChapterMapper.deleteChapterById(id);
            return new Result<String>().success("章节删除成功");
        }
    }
}
