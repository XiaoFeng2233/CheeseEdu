package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/7/11 19:59
 */
@Mapper
@Component
public interface UsersMapper extends BaseMapper<Users> {
    @Select("select * from users where mobile_number = #{num} and status = 1")
    Users getUserByMobileNumber(String num);

    @Select("select * from users where mobile_number = #{authinfo} or username = #{authinfo} and status = 1")
    Users getUserByMobileNumberOrUsername(String authinfo);

    @Select("select * from users where status = 1")
    IPage<Users> selectAllUser(Page<?> page);

    @Select("select * from users where username = #{param} or mobile_number = #{param} and status = 1")
    List<Users> selectUsersByUserNameOrMobileNumber(String param);

    @Select("select * from users where username = #{username} and status = 1")
    Users selectUserByUserName(String username);

    @Select("select * from users where mobile_number = #{number} and status = 1")
    Users selectUserByMobileNumber(String number);

    @Select("select *,(select name from vip_package where id = vip_package_id) as vip_package_name from users where is_vip = 1 and status = 1")
    IPage<Users> selectVipUsers(Page<?> page);

    @Select("select *,(select name from vip_package where id = vip_package_id) as vip_package_name from users where (username = #{param} or mobile_number = #{param}) and is_vip = 1 and status = 1")
    List<Users> selectVipUsersByUserNameOrMobileNumber(String param);

    @Select("select *,(select name from vip_package where id = vip_package_id) as vip_package_name from users where id = #{userId} and status = 1")
    Users selectUserById(int userId);

    @Update("update users set nickname=#{newName} where id = #{id} and status = 1")
    Integer updateUserNickNameByUserId(int id,String newName);

    @Update("update users set avatar=#{url} where id = #{id} and status = 1")
    Integer updateUserAvatarByUserId(int id,String url);

    @Update("update users set is_vip = 0 where is_vip = 1 and vip_expired_time < now() and status = 1")
    Integer updateUserVipExpired();

    @Select("select count(*) from users where TO_DAYS(created_time) = TO_DAYS(CURDATE()) and status = 1")
    Integer selectTodayNewUsersCount();

    @Update("update users set status = 0,is_lock = 1 where id = #{userId} and status = 1")
    Integer deleteUserById(int userId);

}
