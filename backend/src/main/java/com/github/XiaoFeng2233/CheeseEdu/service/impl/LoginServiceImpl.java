package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.github.XiaoFeng2233.CheeseEdu.dao.LogMapper;
import com.github.XiaoFeng2233.CheeseEdu.dao.UsersMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Log;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Users;
import com.github.XiaoFeng2233.CheeseEdu.service.LoginService;
import com.github.XiaoFeng2233.CheeseEdu.util.JWTUtil;
import com.github.XiaoFeng2233.CheeseEdu.util.MyUtils;
import com.github.XiaoFeng2233.CheeseEdu.util.RedisUtil;
import com.github.XiaoFeng2233.CheeseEdu.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/8/2 19:02
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private SmsUtil smsUtil;

    @Override
    public Result<String> sendLoginVerifyCode(String mobileNumber) {
        Boolean isMobile = MyUtils.checkMobileNumber(mobileNumber);
        if (isMobile) {
            Object isBlock = redisUtil.get("login-block:" + mobileNumber);
            if (isBlock != null) {
                return new Result<String>().error( "用户被锁定,请稍后尝试登录");
            }
            Object code = redisUtil.get("login-verifycode:" + mobileNumber);
            if (code != null) {
                return new Result<String>().error("发送频率过快");
            }
            Users users = usersMapper.getUserByMobileNumber(mobileNumber);
            if (users != null) {
                String verifyCode = RandomUtil.randomNumbers(6);
                redisUtil.setEx("login-verifycode:" + mobileNumber, verifyCode, 120, TimeUnit.SECONDS);
                //发送验证码操作
                Boolean b = smsUtil.sendLoginCode(mobileNumber,verifyCode);
                if (b){
                    return new Result<String>().success("短信发送成功");
                }else{
                    return new Result<String>().error("短信发送失败,请联系网站管理员");
                }
            } else {
                return new Result<String>().error( "手机号码不存在");
            }
        } else {
            return new Result<String>().error("手机号格式不正确");
        }


    }

    @Override
    public Result<String> loginWithVerifyCode(HttpServletRequest request, String mobileNumber, String verifyCode) {
        Boolean isMobile = MyUtils.checkMobileNumber(mobileNumber);
        if (isMobile || !StrUtil.hasBlank(verifyCode)) {
            Object isBlock = redisUtil.get("login-block:" + mobileNumber);
            if (isBlock != null) {
                return new Result<String>().error("用户被锁定,请稍后尝试登录");
            }
            Object code = redisUtil.get("login-verifycode:" + mobileNumber);
            if (code != null) {
                if (verifyCode.equalsIgnoreCase((String) code)) {
                    Users user = usersMapper.getUserByMobileNumber(mobileNumber);
                    if (user == null){
                        return new Result<String>().error( "手机号不存在");
                    }
                    redisUtil.delete("login-fail:" + mobileNumber);
                    String jwt = jwtUtil.createToekn(user,7);
                    Log log = new Log();
                    log.setEvent("login");
                    log.setCreatedTime(new Date());
                    log.setUserId(user.getId());
                    log.setIp(MyUtils.getIp(request));
                    log.setArea(MyUtils.getIpArea(MyUtils.getIp(request)));
                    logMapper.insert(log);
                    return new Result<String>().success(jwt);
                } else {
                    Object loginFail = redisUtil.get("login-fail:" + mobileNumber);
                    if (loginFail != null) {
                        int loginFailTimes = (Integer)loginFail;
                        if (loginFailTimes > 5) {
                            redisUtil.setEx("login-block:" + mobileNumber, "true", 600, TimeUnit.SECONDS);
                        } else {
                            redisUtil.incrBy("login-fail:" + mobileNumber, 1);
                        }
                    } else {
                        redisUtil.setEx("login-fail:" + mobileNumber, 1, 300, TimeUnit.SECONDS);
                    }
                    return new Result<String>().error("验证码输入有误");
                }
            } else {
                return new Result<String>().error( "验证码输入有误");
            }

        } else {
            return new Result<>().wrongParams();
        }

    }

    @Override
    public Result<String> loginWithUsername(HttpServletRequest request, String authInfo, String password) {
        if (StrUtil.hasBlank(authInfo, password)) {
            return new Result<>().wrongParams();
        } else {
            Object isBlock = redisUtil.get("login-block:" + authInfo);
            if (isBlock != null) {
                return new Result<String>().error("用户被锁定,请稍后尝试登录");
            }
            Users users = usersMapper.getUserByMobileNumberOrUsername(authInfo);
            Object loginFail = redisUtil.get("login-fail:" + authInfo);
            if (users != null) {
                String salt = users.getSalt();
                String encryptPassword = SecureUtil.md5(password + salt);
                if (encryptPassword.equalsIgnoreCase(users.getPassword())) {
                    redisUtil.delete("login-fail:" + authInfo);
                    String jwt = jwtUtil.createToekn(users,7);
                    Log log = new Log();
                    log.setEvent("login");
                    log.setCreatedTime(new Date());
                    log.setUserId(users.getId());
                    log.setIp(MyUtils.getIp(request));
                    log.setArea(MyUtils.getIpArea(MyUtils.getIp(request)));
                    logMapper.insert(log);
                    return new Result<String>().success(jwt);
                } else {
                    if (loginFail != null) {
                        int loginFailTimes = (Integer) loginFail;
                        if (loginFailTimes > 5) {
                            redisUtil.setEx("login-block:" + authInfo, "true", 600, TimeUnit.SECONDS);
                        } else {
                            redisUtil.incrBy("login-fail:" + authInfo, 1);
                        }
                    } else {
                        redisUtil.setEx("login-fail:" + authInfo, 1, 300, TimeUnit.SECONDS);
                    }
                    return new Result<String>().error( "用户名或密码错误");
                }
            } else {
                if (loginFail != null) {
                    int loginFailTimes = (Integer) loginFail;
                    if (loginFailTimes > 5) {
                        redisUtil.setEx("login-block:" + authInfo, "true", 600, TimeUnit.SECONDS);
                    } else {
                        redisUtil.incrBy("login-fail:" + authInfo, 1);
                    }
                } else {
                    redisUtil.setEx("login-fail:" + authInfo, 1, 300, TimeUnit.SECONDS);
                }                return new Result<String>().error("用户名或密码错误");
            }
        }
    }


    @Override
    public Result<String> sendResetVerifyCode(String mobileNumber) {
        if (!MyUtils.checkMobileNumber(mobileNumber)) {
            return new Result<>().wrongParams();
        }
        Users user = usersMapper.getUserByMobileNumber(mobileNumber);
        if (user == null){
            return new Result<String>().error("手机号不存在");
        }

        Object code = redisUtil.get("reset-verifycode:" + mobileNumber);
        if (code !=null){
            return new Result<String>().error("发送频率过快");
        }

        String verifyCode = RandomUtil.randomNumbers(6);
        smsUtil.sendForgetCode(mobileNumber,verifyCode);
        redisUtil.setEx("reset-verifycode:" + mobileNumber,verifyCode,120,TimeUnit.SECONDS);
        return new Result<String>().success("发送成功");
    }

    @Override
    public Result<String> resetPassword(String mobileNumber, String verifycode, String newpassword) {
        if (StrUtil.hasBlank(mobileNumber,verifycode,newpassword)){
            return new Result<>().wrongParams();
        }
        Object code = redisUtil.get("reset-verifycode:" + mobileNumber);

        if (code == null) {
            return new Result<String>().error("验证码输入有误");
        }
        if (!verifycode.equalsIgnoreCase((String)code)){
            return new Result<String>().error("验证码输入有误");
        }
        Users user = usersMapper.getUserByMobileNumber(mobileNumber);
        String salt = RandomUtil.randomString(50);
        user.setPassword(SecureUtil.md5(newpassword + salt));
        user.setSalt(salt);
        usersMapper.updateById(user);
        return new Result<String>().success("密码修改成功");
    }
}
