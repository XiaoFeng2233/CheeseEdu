package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.dao.CourseCommentMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseComment;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseCommentService;
import com.github.XiaoFeng2233.CheeseEdu.service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseCommentServiceImpl implements CourseCommentService {
    @Autowired
    private CourseCommentMapper courseCommentMapper;
    @Autowired
    private UserCourseService userCourseService;
    @Override
    public Result<List<CourseComment>> getCourseCommentByCourseId(int courseId) {
        List<CourseComment> courseComments = courseCommentMapper.selectCommentsByCourseId(courseId);
        return new Result<List<CourseComment>>().success(courseComments);
    }

    @Override
    public Result<String> addCourseCommentByCourseId(float rating,String comment,int courseId,int userId) {
        Boolean aBoolean = userCourseService.checkUserAndCourseExist(userId,courseId);
        if (aBoolean){
            CourseComment courseComment = new CourseComment();
            courseComment.setCourseId(courseId);
            courseComment.setContent(comment);
            courseComment.setUserId(userId);
            courseComment.setCreatedTime(new Date());
            courseComment.setUpdatedTime(new Date());
            courseComment.setIsDelete(0);
            courseComment.setStar(rating);
            courseComment.setStatus(1);
            courseCommentMapper.insert(courseComment);
            return new Result<String>().success("评论发布成功");
        }else{
            return new Result<String>().error("您还未拥有该课程");
        }
    }

    @Override
    public Result<IPage<CourseComment>> getCourseCommentList(int page, int count) {
        Page<CourseComment> pageOpt = new Page<>(page,count);
        return new Result<IPage<CourseComment>>().success(courseCommentMapper.selectCourseComment(pageOpt));
    }

    @Override
    public Result<String> deleteCommentById(int id) {
        courseCommentMapper.deleteCourseCommentById(id);
        return new Result<String>().success("评论删除成功");
    }
}
