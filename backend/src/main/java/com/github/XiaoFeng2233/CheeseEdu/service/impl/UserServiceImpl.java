package com.github.XiaoFeng2233.CheeseEdu.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.dao.GlobalConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.dao.UsersMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.GlobalConfig;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Users;
import com.github.XiaoFeng2233.CheeseEdu.service.UserService;
import com.github.XiaoFeng2233.CheeseEdu.util.MyUtils;
import com.github.XiaoFeng2233.CheeseEdu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;


/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/7/11 18:39
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private GlobalConfigMapper globalConfigMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result<IPage<Users>> getAllUser(Integer pageNumber, Integer pageSize) {
        Page<Users> page = new Page<>(pageNumber,pageSize);
        IPage<Users> usersIPage = usersMapper.selectAllUser(page);
        return new Result<IPage<Users>>().success(usersIPage);
    }


    @Override
    public Result<List<Users>> getUserByUserNameOrMobileNumber(String param) {
        List<Users> users = usersMapper.selectUsersByUserNameOrMobileNumber(param);
        return new Result<List<Users>>().success(users);
    }

    @Override
    public Result<String> addUser(Users users, HttpServletRequest request) {
        if (usersMapper.selectUserByUserName(users.getUsername())!=null){
            return new Result<String>().error("添加失败,用户名已存在");
        }

        if (usersMapper.selectUserByMobileNumber(users.getMobileNumber())!=null){
            return new Result<String>().error("添加失败,手机号已存在");
        }
        GlobalConfig config = globalConfigMapper.selectById(1);
        String salt = RandomUtil.randomString(50);
        String pwd = SecureUtil.md5(users.getPassword() + salt);

        users.setSalt(salt);
        users.setPassword(pwd);
        users.setAvatar(config.getDefaultAvatar());
        users.setCreatedTime(new Date());
        users.setIsLock(0);
        users.setStatus(1);
        users.setRegIp(MyUtils.getIp(request));
        users.setRegArea(MyUtils.getIpArea(MyUtils.getIp(request)));
        users.setUpdatedTime(new Date());

        int i = usersMapper.insert(users);
        if (i > 0) {
            return new Result<String>().success("添加成功");
        }
        return new Result<String>().error("添加失败,发生未知错误");
    }

    @Override
    public Result<IPage<Users>> getVipUsers(Integer pageNumber, Integer pageSize) {
        Page<Users> page = new Page<>(pageNumber,pageSize);
        IPage<Users> usersIPage = usersMapper.selectVipUsers(page);
        return new Result<IPage<Users>>().success(usersIPage);
    }

    @Override
    public Result<List<Users>> getVipUsersByUserNameOrMobileNumber(String param) {
        List<Users> users = usersMapper.selectVipUsersByUserNameOrMobileNumber(param);
        return new Result<List<Users>>().success(users);
    }

    @Override
    public Result<Users> getUserById(int userId) {
        Users user = usersMapper.selectUserById(userId);
        Users u = new Users();
        u.setId(user.getId());
        u.setNickname(user.getNickname());
        u.setUsername(user.getUsername());
        u.setAvatar(user.getAvatar());
        u.setIsVip(user.getIsVip());
        u.setIsAdmin(user.getIsAdmin());
        u.setVipPackageName(user.getVipPackageName());
        u.setVipPackageId(user.getVipPackageId());
        u.setVipExpiredTime(user.getVipExpiredTime());
        u.setMobileNumber(DesensitizedUtil.mobilePhone(user.getMobileNumber()));
        return new Result<Users>().success(u);
    }

    @Override
    public Result<String> updatePassword(int userId, String oldPassword, String newPassword) {
        Users user = usersMapper.selectUserById(userId);
        String salt = user.getSalt();
        String pwd = SecureUtil.md5(oldPassword + salt);
        if (pwd.equalsIgnoreCase(user.getPassword())){
            salt = RandomUtil.randomString(50);
            user.setSalt(salt);
            user.setPassword(SecureUtil.md5(newPassword + salt));
            usersMapper.updateById(user);
            return new Result<String>().success("密码修改成功");
        }else{
            return new Result<String>().error("旧密码输入密码错误");
        }
    }

    @Override
    public Result<String> updateUserNickName(int userId, String newNickName) {
        int  i = usersMapper.updateUserNickNameByUserId(userId,newNickName);
        if(i>0){
            return new Result<String>().success("修改成功");
        }else{
            return new Result<String>().error("修改失败");
        }
    }

    @Override
    public Result<String> updateUserAvatar(int userId, MultipartFile file) {
        try {
            MyUtils myUtil = new MyUtils();
            String path = myUtil.uploadImage(file);
            if (path == null){
                return new Result<String>().error("未被允许的文件格式");
            }
            int i = usersMapper.updateUserAvatarByUserId(userId,path);
            if(i>0){
                return new Result<String>().success("修改成功");
            }else{
                return new Result<String>().error("修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result<String>().error("系统发生未知错误");
        }
    }

    @Override
    public int updateUserById(int userId, Users user) {
        user.setId(userId);
        int i = usersMapper.updateById(user);
        return i;
    }

    @Override
    public Integer updateUserVipExpired() {
        return usersMapper.updateUserVipExpired();
    }

    @Override
    public Integer getTodayNewUsersCount() {
        return usersMapper.selectTodayNewUsersCount();
    }

    @Override
    public Result<Users> getUserByMobileNumber(String number) {
        Users users = usersMapper.selectUserByMobileNumber(number);
        return new Result<Users>().success(users);
    }

    @Override
    public Result<String> resetUserPhoneNumber(String oldNumber, String newNumber, String verifyCode,String password) {
        Object o = redisUtil.get("reset:" + newNumber);
        if (o==null){
            return new Result<String>().error("验证码输入错误,请重新获取");
        }
        String v = (String) o;
        if (!((String) o).equalsIgnoreCase(v)){
            return new Result<String>().error("验证码输入错误,请重新获取");
        }

        Users users = usersMapper.selectUserByMobileNumber(oldNumber);
        if (users == null){
            return new Result<String>().error("输入的旧手机号有误");
        }else{
            String encryptPassword = SecureUtil.md5(password + users.getSalt());
            if (encryptPassword.equalsIgnoreCase(users.getPassword())){
                users.setMobileNumber(newNumber);
                usersMapper.updateById(users);
                return new Result<String>().success("手机号码修改成功");
            }else{
                return new Result<String>().error("密码输入错误");
            }
        }
    }

    @Override
    public Result<Users> getUserByIdA(int id) {
        Users users = usersMapper.selectUserById(id);
        return new Result<Users>().success(users);
    }

    @Override
    public Result<String> updateUserVip(int userId, Date expireTime, int vipPackageId) {
        Users user = new Users();
        user.setId(userId);
        user.setVipExpiredTime(expireTime);
        user.setVipPackageId(vipPackageId);
        user.setIsVip(1);
        usersMapper.updateById(user);
        return new Result<String>().success("用户会员信息修改成功");
    }

    @Override
    public Result<String> deleteUserById(int id) {
        usersMapper.deleteUserById(id);
        return new Result<String>().success("用户删除成功");
    }
}
