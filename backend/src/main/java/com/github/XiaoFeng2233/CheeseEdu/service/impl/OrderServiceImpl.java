package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.aliyun.tea.TeaUnretryableException;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.dao.OrderMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;
import com.github.XiaoFeng2233.CheeseEdu.service.*;
import com.github.XiaoFeng2233.CheeseEdu.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.matchers.Or;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentConfigService paymentConfigService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private VipPackageService vipPackageService;

    final String ALIPAY_API = "openapi.alipay.com";

    @Value("${order.expire}")
    private int expireSeconds;

    @Override
    @Transactional
    public Result<String> createCourseOrder(HttpServletRequest request, int courseId) {
        Result<Course> c = courseService.getActiveCourseById(courseId);
        if (c == null) {
            return new Result<String>().error("找不到课程");
        }
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        Result<Users> u = userService.getUserById(userId.asInt());
        Users user = u.getData();
        Course course = c.getData();
        if (userCourseService.checkUserAndCourseExist(user.getId(),course.getId())){
            return new Result<String>().error("订单创建失败,您已拥有该课程");
        }
        Order order = new Order();
        order.setOrderNum(IdUtil.simpleUUID());
        order.setUserId(user.getId());
        order.setOrderName(course.getTitle());
        order.setExpireTime(DateUtil.offset(DateUtil.date(), DateField.MILLISECOND,expireSeconds));
        //免费订单
        if (course.getType() == 0){
            return freeOrder(course,userId,order);
        }else if(course.getType() == 1){
            //会员免费
            if (user.getIsVip() == 1){
                return vipFreeOrder(course,userId,order,user);
            }
            //非会员
            return normalOrder(course,userId,order);
        }else if (course.getType() == 2){
            //收费
            return normalOrder(course,userId,order);
        }else{
            return new Result<String>().error("订单创建失败,发生未知错误");
        }
    }

    @Override
    @Transactional
    public Result<String> createVipOrder(HttpServletRequest request, int vipPackageId) {
        Result<VipPackage> vipPackageById = vipPackageService.getVipPackageById(vipPackageId);
        if (vipPackageById.getData() == null){
            return new Result<String>().error("找不到VIP套餐");
        }
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        Result<Users> u = userService.getUserById(userId.asInt());
        Users user = u.getData();
        System.out.println(user);
        if (user.getIsVip() == 1){
            return new Result<String>().error("您的VIP套餐还未到期,请到期后在购买新的套餐");
        }

        Order order = new Order();
        order.setType(2);
        order.setIsFree(0);
        order.setOrderNum(IdUtil.simpleUUID());
        order.setUserId(user.getId());
        order.setOrderName(vipPackageById.getData().getName() + "  " + vipPackageById.getData().getDuration() + "天");
        order.setExpireTime(DateUtil.offset(DateUtil.date(), DateField.MILLISECOND,expireSeconds));
        order.setCreatedTime(new Date());
        order.setStatus(1);
        order.setIsPayOk(0);
        order.setUpdatedTime(new Date());
        order.setCourseId(0);
        order.setPrice(vipPackageById.getData().getPrice());
        order.setVipPackageId(vipPackageById.getData().getId());
        rabbitTemplate.convertAndSend("direct_order_exchange","order.ttl.expire.notice",order.getOrderNum());
        orderMapper.insert(order);
        return new Result<String>().success(order.getOrderNum());
    }


    private  Result<String> freeOrder(Course course, Claim userId, Order order){
        order.setCreatedTime(new Date());
        order.setIsFree(1);
        order.setStatus(1);
        order.setIsPayOk(1);
        order.setType(1);
        order.setUpdatedTime(new Date());
        order.setCourseId(course.getId());
        order.setPrice(new BigDecimal(0));
        order.setVipPackageId(0);
        orderMapper.insert(order);

        UserCourse userCourse = new UserCourse();
        userCourse.setCourseId(course.getId());
        userCourse.setUserId(userId.asInt());
        userCourse.setPrice(new BigDecimal(0));
        userCourse.setStatus(1);
        userCourse.setCreatedTime(new Date());
        userCourse.setIsFree(1);
        userCourseService.addUserCourse(userCourse);
        courseService.increasingStudentNumberById(course.getId());
        return new Result<String>().success(order.getOrderNum());
  }


    private Result<String> vipFreeOrder(Course course,Claim userId,Order order,Users user){
        order.setCreatedTime(new Date());
        order.setIsFree(1);
        order.setStatus(1);
        order.setIsPayOk(1);
        order.setType(1);
        order.setUpdatedTime(new Date());
        order.setCourseId(course.getId());
        order.setPrice(new BigDecimal(0));
        order.setVipPackageId(user.getVipPackageId());
        orderMapper.insert(order);

        UserCourse userCourse = new UserCourse();
        userCourse.setCourseId(course.getId());
        userCourse.setUserId(userId.asInt());
        userCourse.setPrice(new BigDecimal(0));
        userCourse.setStatus(1);
        userCourse.setCreatedTime(new Date());
        userCourse.setIsFree(1);
        userCourseService.addUserCourse(userCourse);
        courseService.increasingStudentNumberById(course.getId());

        return new Result<String>().success(order.getOrderNum());
    }


    private  Result<String> normalOrder(Course course,Claim userId,Order order){
        rabbitTemplate.convertAndSend("direct_order_exchange","order.ttl.expire.notice",order.getOrderNum());
        order.setCreatedTime(new Date());
        order.setIsFree(0);
        order.setStatus(1);
        order.setIsPayOk(0);
        order.setType(1);
        order.setUpdatedTime(new Date());
        order.setCourseId(course.getId());
        order.setPrice(course.getPrice());
        order.setVipPackageId(0);
        orderMapper.insert(order);
        return new Result<String>().success(order.getOrderNum());
    }

    @Override
    public Result<Order> getOrderByOrderNum(String orderNum) {
        Order orderByOrderNum = orderMapper.getOrderByOrderNum(orderNum);
        return new Result<Order>().success(orderByOrderNum);
    }

    @Override
    public Result<String> getPayQrcode(String orderNum, int userId) {
        Order order = getOrderByUserAndOrderNum(userId,orderNum).getData();
        if (order==null){
            return new Result<String>().error("订单不存在或已过期");
        }

        Result<PaymentConfig> paymentConfig = paymentConfigService.getPaymentConfig();
        PaymentConfig config = paymentConfig.getData();

        Config c = new Config();
        c.protocol = "https";
        c.gatewayHost = ALIPAY_API;
        c.signType = "RSA2";
        c.appId = config.getAppId();
        c.merchantPrivateKey = config.getMerchantPrivateKey();
        c.alipayPublicKey = config.getAlipayPublicKey();
        c.notifyUrl = config.getNotifyUrl();

        Factory.setOptions(c);
        try {
            AlipayTradePrecreateResponse res = Factory.Payment.FaceToFace().preCreate(order.getOrderName(),order.getOrderNum(),order.getPrice().toString());
            if (ResponseChecker.success(res)){
                return new Result<String>().success(res.qrCode);
            }else{
                log.error("调用失败，原因：" + res.msg + "，" + res.subMsg);
            }
        }catch (TeaUnretryableException e){
            e.printStackTrace();
            log.error("支付宝调用遭遇异常");
            return new Result<String>().error("系统出现未知错误");
        }catch (Exception e){
            log.error("支付宝调用遭遇异常");
            return new Result<String>().error("系统出现未知错误");
        }
        return new Result<String>().error("系统出现未知错误");
    }

    @Override
    public Result<Order> getOrderByUserAndOrderNum(Integer userId,String orderNum) {
        Order order = orderMapper.getOrderByOrderNumAndUser(userId,orderNum);
        if (order == null){
            return new Result<Order>().error(null);
        }else{
            return new Result<Order>().success(order);
        }
    }

    @Override
    public int updateOrderByOrderNum(Order order,String orderNum) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_num",orderNum);
        int i  = orderMapper.update(order,wrapper);
        return i;
    }

    @Override
    public Result<List<Order>> getOrderListByUserId(int userId) {
        return new Result<List<Order>>().success(orderMapper.getOrderListByUserId(userId));
    }

    @Override
    public int updateOrderExpire(String orderNum) {
        return orderMapper.updateOrderExpireByOrderNum(orderNum);
    }

    @Override
    public int cancelOrder(int userId, String orderNum) {
        return orderMapper.updateOrderExpireByUserIdAndOrderNum(userId,orderNum);
    }

    @Override
    public Result<String> renewVipPackage(int userId) {
        Result<Users> userById = userService.getUserById(userId);
        if (userById.getData().getIsVip() == 0){
            return new Result<String>().error("您还不是VIP,无法续费");
        }else{
            Integer vipPackageId = userById.getData().getVipPackageId();
            Result<VipPackage> vipPackage = vipPackageService.getVipPackageById(vipPackageId);
            Order order = new Order();
            order.setType(3);
            order.setIsFree(0);
            order.setOrderNum(IdUtil.simpleUUID());
            order.setUserId(userById.getData().getId());
            order.setOrderName("续费"+ "   " + vipPackage.getData().getName() + "  " + vipPackage.getData().getDuration() + "天");
            order.setExpireTime(DateUtil.offset(DateUtil.date(), DateField.MILLISECOND,expireSeconds));
            order.setCreatedTime(new Date());
            order.setStatus(1);
            order.setIsPayOk(0);
            order.setUpdatedTime(new Date());
            order.setCourseId(0);
            order.setPrice(vipPackage.getData().getPrice());
            order.setVipPackageId(vipPackage.getData().getId());
            rabbitTemplate.convertAndSend("direct_order_exchange","order.ttl.expire.notice",order.getOrderNum());
            orderMapper.insert(order);
            return new Result<String>().success(order.getOrderNum());
        }
    }

    @Override
    public BigDecimal getTodayOrderAmount() {
        return orderMapper.selectTodayMount();
    }

    @Override
    public Integer getTodayOrderCount() {
        return orderMapper.selectTodayOrderCount();
    }

    @Override
    public Result<IPage<Order>> getOrder(int page,int count) {
        Page<Order> p = new Page<>(page,count);
        IPage<Order> iPage = orderMapper.selectOrder(p);
        return new Result<IPage<Order>>().success(iPage);
    }

    @Override
    public Result<Order> getOrderById(int orderId) {
        Order order = orderMapper.selectOrderById(orderId);
        return new Result<Order>().success(order);
    }
}
