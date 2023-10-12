package com.github.XiaoFeng2233.CheeseEdu.service;


import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.Order;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Result<String> createCourseOrder(HttpServletRequest request, int courseId);
    Result<String> createVipOrder(HttpServletRequest request,int vipPackageId);
    Result<Order> getOrderByOrderNum(String orderNum);
    Result<String> getPayQrcode(String orderNum, int userId);
    Result<Order> getOrderByUserAndOrderNum(Integer userId,String orderNum);
    int updateOrderByOrderNum(Order order,String orderNum);
    Result<List<Order>> getOrderListByUserId(int userId);
    int updateOrderExpire(String orderNum);
    int cancelOrder(int userId,String orderNum);
    Result<String> renewVipPackage(int userId);
    Integer getTodayOrderCount();
    BigDecimal getTodayOrderAmount();
    Result<IPage<Order>> getOrder(int page,int count);
    Result<Order> getOrderById(int orderId);
}
