package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.UserCourseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.UserCourse;
import com.github.XiaoFeng2233.CheeseEdu.service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    @Autowired
    private UserCourseMapper userCourseMapper;

    @Override
    public int addUserCourse(UserCourse userCourse) {
        return userCourseMapper.insert(userCourse);
    }

    @Override
    public Boolean checkUserAndCourseExist(int userId, int courseId) {
        List<UserCourse> userCourses = userCourseMapper.selectUserCourseByUserIdAndCourseId(userId, courseId);
        System.out.println(userCourses.size());
        System.out.println(userId);
        System.out.println(courseId);
        if (userCourses.size()>0){
            return true;
        }
        return false;
    }

    @Override
//    @Cacheable(value = "user-course",key = "#userId")
    public Result<List<UserCourse>> getCourseListByUser(int userId) {

//        userCourseMapper.select
        return null;
    }
}
