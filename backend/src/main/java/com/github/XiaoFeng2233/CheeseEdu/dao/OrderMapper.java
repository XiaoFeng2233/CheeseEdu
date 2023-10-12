package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.entity.Order;
import com.github.XiaoFeng2233.CheeseEdu.entity.Question;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Component
public interface OrderMapper extends BaseMapper<Order> {

    @Select("select *,(select username from users where id = t_order.user_id) as username from t_order where order_num = #{orderNum}")
    Order getOrderByOrderNum(String orderNum);

    @Select("select * from t_order where order_num = #{orderNum} and status = 1 and user_id = #{userId}")
    Order getOrderByOrderNumAndUser(int userId,String orderNum);

    @Select("select *,(select image from course where id = t_order.course_id) as course_image from t_order where user_id = #{userId}")
    Order getOrderByUserId(int userId);

    @Select("select *,(select image from course where id = t_order.course_id) as course_image from t_order where user_id = #{userId} order by created_time desc")
    List<Order> getOrderListByUserId(int userId);

    @Update("update t_order set status = 0 where user_id = #{userId} and order_num = #{orderNum}")
    int updateOrderExpireByUserIdAndOrderNum(int userId,String orderNum);


    @Update("update t_order set status = 0 where order_num = #{orderNum}")
    int updateOrderExpireByOrderNum(String orderNum);

    @Select("select count(*) from t_order where TO_DAYS(created_time) = TO_DAYS(CURDATE())")
    Integer selectTodayOrderCount();

    @Select("select sum(price) from t_order where TO_DAYS(created_time) = TO_DAYS(CURDATE()) and is_pay_ok = 1")
    BigDecimal selectTodayMount();

    @Select("select *,(select username from users where id = t_order.user_id) as username from t_order order by created_time desc")
    IPage<Order> selectOrder(Page<Order> page);

    @Select("select *,(select username from users where id = t_order.user_id) as username,(select title from course where id = t_order.course_id) as course_title,(select name from vip_package where id = t_order.vip_package_id) as vipPackageName from t_order where id = #{orderId}")
    Order selectOrderById(int orderId);


}
