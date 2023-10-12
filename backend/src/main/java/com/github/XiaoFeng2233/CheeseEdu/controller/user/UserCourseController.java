package com.github.XiaoFeng2233.CheeseEdu.controller.user;

import com.auth0.jwt.interfaces.Claim;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.UserCourse;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseCommentService;
import com.github.XiaoFeng2233.CheeseEdu.service.UserCourseService;
import com.github.XiaoFeng2233.CheeseEdu.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/do/course")
public class UserCourseController {
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private CourseCommentService courseCommentService;

    @GetMapping("/getcourselist.do")
    public Result<List<UserCourse>> getCourseList(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return userCourseService.getCourseListByUser(userId.asInt());
    }

    @PostMapping("/addcomment.do")
    public Result<String> addComment(@RequestParam("rating")float rating,@RequestParam("comment")String comment,@RequestParam("courseId")int courseId,HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return courseCommentService.addCourseCommentByCourseId(rating,comment,courseId, userId.asInt());
    }
}
