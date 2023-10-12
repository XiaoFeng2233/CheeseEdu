package com.github.XiaoFeng2233.CheeseEdu.controller.user;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import com.auth0.jwt.interfaces.Claim;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseService;
import com.github.XiaoFeng2233.CheeseEdu.service.LogService;
import com.github.XiaoFeng2233.CheeseEdu.service.UserService;
import com.github.XiaoFeng2233.CheeseEdu.util.JWTUtil;
import com.github.XiaoFeng2233.CheeseEdu.util.MyUtils;
import com.github.XiaoFeng2233.CheeseEdu.util.RedisUtil;
import com.github.XiaoFeng2233.CheeseEdu.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/11/11 18:27
 */
@RestController
@RequestMapping("/do/user")
public class UserController {
    @Autowired
    private LogService logService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/getlog.do")
    public Result<List<Log>> getLog(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return logService.getLogByUserId(userId.asInt(),20);
    }

    @GetMapping("/getuserinfo.do")
    public Result<Users> getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return userService.getUserById(userId.asInt());
    }

    @PostMapping("/updatepassword.do")
    public Result<String> updatePassword(@RequestParam String oldpassword,@RequestParam String newpassword,HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return userService.updatePassword(userId.asInt(),oldpassword,newpassword);
    }

    @GetMapping("/changenickname.do/{newName}")
    public Result<String> changeNickName(HttpServletRequest request, @PathVariable("newName")String newName){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return userService.updateUserNickName(userId.asInt(), newName);
    }

    @PostMapping("/changeavatar.do")
    public Result<String> changeAvatar(HttpServletRequest request, @RequestParam("file")MultipartFile file){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return userService.updateUserAvatar(userId.asInt(),file);
    }

    @GetMapping("/getmycourse.do/{count}")
    public Result<List<UserCourse>> getMyCourse(HttpServletRequest request, @PathVariable("count") int count){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return courseService.getMyCourse(userId.asInt(), count);
    }

    @GetMapping("/getcoursevideobycourseid.do/{courseId}")
    public Result<Object> getCourseVideoByCourseId(@PathVariable("courseId") int courseId,HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return courseService.getCourseVideoByCourseId(courseId, userId.asInt());
    }

    @GetMapping("/getvideoplayurl/{courseId}/{videoId}")
    public Result<URL> getVideoPlayUrl(@PathVariable("videoId")int videoId,@PathVariable("courseId")int courseId,HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return courseService.getVideoPlayUrl(courseId,userId.asInt(),videoId);
    }

    @GetMapping("/getuservipinfo.do")
    public Result<Users> getUserVipInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return userService.getUserById(userId.asInt());
    }


    @GetMapping("/sendresetphoneverifycode.do/{newPhone}")
    public Result<String> sendResetPhoneVerifyCode(@PathVariable("newPhone") String newPhone){
        Boolean v = MyUtils.checkMobileNumber(newPhone);
        if (!v){
            return new Result<String>().error("手机号输入错误");
        }
        Result<Users> userByMobileNumber = userService.getUserByMobileNumber(newPhone);
        if (userByMobileNumber.getData() != null){
            return new Result<String>().error("修改失败,手机号码已存在");
        }else{
            String verifyCode = RandomUtil.randomNumbers(6);
            redisUtil.setEx("reset:" + newPhone,verifyCode,120, TimeUnit.SECONDS);
            Boolean aBoolean = smsUtil.sendResetCode(newPhone, verifyCode);
            if (aBoolean){
                return new Result<String>().success("验证码发送成功");
            }else{
                return new Result<String>().error("验证码发送失败,请联系网站管理员");
            }
        }
    }

    @PostMapping("/resetphonenumber.do")
    public Result<String> resetPhoneNumber(@RequestParam("oldNumber")String oldNumber,@RequestParam("newNumber")String newNumber,@RequestParam("verifyCode")String verifyCode,@RequestParam("password")String password){
        return userService.resetUserPhoneNumber(oldNumber,newNumber,verifyCode,password);
    }

}
