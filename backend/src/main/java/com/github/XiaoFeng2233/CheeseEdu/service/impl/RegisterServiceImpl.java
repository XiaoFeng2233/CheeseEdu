package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.XiaoFeng2233.CheeseEdu.dao.GlobalConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.dao.UsersMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.GlobalConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Users;
import com.github.XiaoFeng2233.CheeseEdu.service.GlobalConfigService;
import com.github.XiaoFeng2233.CheeseEdu.service.RegisterService;
import com.github.XiaoFeng2233.CheeseEdu.util.MyUtils;
import com.github.XiaoFeng2233.CheeseEdu.util.RedisUtil;
import com.github.XiaoFeng2233.CheeseEdu.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/8/2 19:44
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private GlobalConfigMapper globalConfigMapper;

    @Override
    public Result<String> sendRegisterCode(String number) {
        if (MyUtils.checkMobileNumber(number)){
            QueryWrapper<Users> wrapper = new QueryWrapper<>();
            wrapper.eq("mobile_number",number);
            Users users = usersMapper.selectOne(wrapper);
            if (users!=null){
                return new Result<String>().error("手机号已经存在");
            }else{
                Object code = redisUtil.get("reg-code:" + number);
                if (code != null){
                    return new Result<String>().error( "发送频率过高");
                }else{
                    String verifyCode = RandomUtil.randomNumbers(6);
                    redisUtil.setEx("reg-code:" + number,verifyCode,120, TimeUnit.SECONDS);
                    //发送验证码操作
                    Boolean b = smsUtil.sendRegCode(number,verifyCode);
                    if (b){
                        return new Result<String>().success("短信发送成功");
                    }else{
                        return new Result<String>().error("短信发送失败,请联系网站管理员");
                    }
                }
            }
        }else{
            return new Result<String>().error("手机号格式不正确");
        }
    }

    @Override
    public Result<String> register(String username, String password, String number, String code, HttpServletRequest request) {
        if (StrUtil.hasBlank(username,password,number,code)){
            return new Result<>().wrongParams();
        }else{
            Object regCode = redisUtil.get("reg-code:" + number);
            if (regCode==null){
                return new Result<String>().error("验证码输入有误");
            }
            if (!code.equalsIgnoreCase((String)regCode)){
                return new Result<String>().error("验证码输入有误");
            }

            if (!MyUtils.checkUsername(username)){
                return new Result<String>().error("用户名不符合规则");
            }

            QueryWrapper<Users> wrapper = new QueryWrapper<>();
            wrapper.eq("username",username);
            Users users = usersMapper.selectOne(wrapper);
            if (users != null){
                return new Result<String>().error("用户名已经存在");
            }else{
                GlobalConfig config = globalConfigMapper.selectById(1);
                String salt = RandomUtil.randomString(50);
                String pwd = SecureUtil.md5(password + salt);
                Users user = new Users();
                user.setUsername(username);
                user.setSalt(salt);
                user.setPassword(pwd);
                user.setAvatar(config.getDefaultAvatar());
                user.setCreatedTime(DateUtil.date());
                user.setIsAdmin(0);
                user.setIsLock(0);
                user.setUpdatedTime(DateUtil.date());
                user.setIsVip(0);
                user.setStatus(1);
                user.setMobileNumber(number);
                user.setVipExpiredTime(DateUtil.lastMonth());
                user.setNickname(RandomUtil.randomStringUpper(8));
                user.setRegIp(MyUtils.getIp(request));
                user.setRegArea(MyUtils.getIpArea(MyUtils.getIp(request)));
                usersMapper.insert(user);
                return new Result<String>().success("register success");
            }
        }
    }
}
