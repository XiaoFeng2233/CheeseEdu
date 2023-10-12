package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.UserCourse;

import java.util.List;

public interface UserCourseService {
    int addUserCourse(UserCourse userCourse);

    Boolean checkUserAndCourseExist(int userId,int courseId);

    Result<List<UserCourse>> getCourseListByUser(int userId);
}
