package com.github.XiaoFeng2233.CheeseEdu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseComment;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import java.util.List;

public interface CourseCommentService {
    Result<List<CourseComment>> getCourseCommentByCourseId(int courseId);
    Result<String> addCourseCommentByCourseId(float rating,String comment,int courseId,int userId);
    Result<IPage<CourseComment>> getCourseCommentList(int page, int count);
    Result<String> deleteCommentById(int id);
}
