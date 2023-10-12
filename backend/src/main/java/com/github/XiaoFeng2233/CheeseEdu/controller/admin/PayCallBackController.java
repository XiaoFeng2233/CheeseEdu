package com.github.XiaoFeng2233.CheeseEdu.controller.admin;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alipay.easysdk.factory.Factory;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;
import com.github.XiaoFeng2233.CheeseEdu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay/callback")
public class PayCallBackController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private UserService userService;
    @Autowired
    private VipPackageService vipPackageService;
    @Autowired
    private CourseService courseService;

    @RequestMapping("/alipay_callback.do")
    public String AliPayCallBack(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String,String> paramList = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            //获取请求头名称的值
            String p = request.getParameter(s);
            paramList.put(s,p);
        }

        String tradeStatus = paramList.get("trade_status");
        String outTradeNo = paramList.get("out_trade_no");
        String tradeNo = paramList.get("trade_no");
        if (StrUtil.hasBlank(tradeStatus) || StrUtil.hasBlank(outTradeNo)){
            return "你想干啥?";
        }
        try {
            Boolean verify = Factory.Payment.Common().verifyNotify(paramList);
            if (verify){
                //通过验证
                if (tradeStatus.equalsIgnoreCase("TRADE_SUCCESS")) {
                    //用户付款成功后的操作
                    Order order = orderService.getOrderByOrderNum(outTradeNo).getData();
                    if (order==null){
                        return "系统出现未知错误!";
                    }
                    //课程订单
                    if (order.getType()==1){
                        order.setPayment("alipay");
                        order.setPaymentNum(tradeNo);
                        order.setIsPayOk(1);
                        order.setUpdatedTime(new Date());
                        orderService.updateOrderByOrderNum(order,order.getOrderNum());
                        UserCourse userCourse = new UserCourse();
                        userCourse.setIsFree(0);
                        userCourse.setCourseId(order.getCourseId());
                        userCourse.setStatus(1);
                        userCourse.setPrice(order.getPrice());
                        userCourse.setCreatedTime(new Date());
                        userCourse.setUserId(order.getUserId());
                        userCourseService.addUserCourse(userCourse);
                        courseService.increasingStudentNumberById(order.getCourseId());
                        return "success";
                        //开通VIP订单
                    }else if (order.getType() == 2){
                        order.setPayment("alipay");
                        order.setPaymentNum(tradeNo);
                        order.setIsPayOk(1);
                        order.setUpdatedTime(new Date());
                        orderService.updateOrderByOrderNum(order,order.getOrderNum());
                        Integer userId = order.getUserId();
                        Result<Users> user = userService.getUserById(userId);
                        if (user.getData() == null){
                            return "系统出现未知错误";
                        }else{
                            Result<VipPackage> vipPackageById = vipPackageService.getVipPackageById(order.getVipPackageId());
                            Users users = new Users();
                            users.setVipExpiredTime(DateUtil.offset(new Date(), DateField.DAY_OF_YEAR,vipPackageById.getData().getDuration()));
                            users.setIsVip(1);
                            userService.updateUserById(userId,users);
                            return "success";
                        }
                        //续费VIP订单
                    }else if (order.getType() == 3){
                        order.setPayment("alipay");
                        order.setPaymentNum(tradeNo);
                        order.setIsPayOk(1);
                        order.setUpdatedTime(new Date());
                        orderService.updateOrderByOrderNum(order,order.getOrderNum());
                        Integer userId = order.getUserId();
                        Result<Users> user = userService.getUserById(userId);
                        if (user.getData() == null){
                            return "系统出现未知错误";
                        }else{
                            Result<VipPackage> vipPackageById = vipPackageService.getVipPackageById(order.getVipPackageId());
                            Users users = new Users();
                            users.setVipExpiredTime(DateUtil.offset(user.getData().getVipExpiredTime(), DateField.DAY_OF_YEAR,vipPackageById.getData().getDuration()));
                            users.setIsVip(1);
                            userService.updateUserById(userId,users);
                            return "success";
                        }
                    }
                    else{
                        return "系统出现未知错误";
                    }
                }
                return "系统出现未知错误";
            }else{
                return "你想干啥?";
            }
        }catch (Exception e){
            return "你想干啥?";
        }
    }


}
