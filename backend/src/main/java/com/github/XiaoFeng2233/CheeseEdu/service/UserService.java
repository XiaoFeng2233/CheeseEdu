package com.github.XiaoFeng2233.CheeseEdu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/7/11 18:38
 */
public interface UserService {
    Result<IPage<Users>> getAllUser(Integer pageNumber, Integer pageSize);
    Result<List<Users>> getUserByUserNameOrMobileNumber(String param);
    Result<String> addUser(Users users, HttpServletRequest request);
    Result<IPage<Users>> getVipUsers(Integer pageNumber, Integer pageSize);
    Result<List<Users>> getVipUsersByUserNameOrMobileNumber(String param);
    Result<Users> getUserById(int userId);
    Result<String> updatePassword(int userId,String oldPassword,String newPassword);
    Result<String> updateUserNickName(int userId,String newNickName);
    Result<String> updateUserAvatar(int userId, MultipartFile file);
    int updateUserById(int userId,Users user);
    Integer updateUserVipExpired();
    Integer getTodayNewUsersCount();
    Result<Users> getUserByMobileNumber(String number);
    Result<String> resetUserPhoneNumber(String oldNumber,String newNumber,String verifyCode,String password);
    Result<Users> getUserByIdA(int id);
    Result<String> updateUserVip(int userId, Date expireTime,int vipPackageId);
    Result<String> deleteUserById(int id);
}
