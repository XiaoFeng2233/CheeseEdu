package com.github.XiaoFeng2233.CheeseEdu.controller.user;

import com.auth0.jwt.interfaces.Claim;
import com.github.XiaoFeng2233.CheeseEdu.entity.Order;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.OrderService;
import com.github.XiaoFeng2233.CheeseEdu.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/do/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/createcourseorder.do")
    public Result<String> createCourseOrder(HttpServletRequest request, @RequestParam int courseId){
        return orderService.createCourseOrder(request,courseId);
    }

    @GetMapping("/getorder.do/{num}")
    public Result<Order> getOrder(HttpServletRequest request,@PathVariable("num") String num){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return orderService.getOrderByUserAndOrderNum(userId.asInt(),num);
    }

    @PostMapping("/getpayurl.do")
    public Result<String> getPayUrl(@RequestParam String orderNum, HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return orderService.getPayQrcode(orderNum,userId.asInt());
    }

    @GetMapping("/checkorder.do/{orderNum}")
    public Result<Order> checkOrder(HttpServletRequest request,@PathVariable("orderNum") String orderNum){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return orderService.getOrderByUserAndOrderNum(userId.asInt(),orderNum);
    }

    @GetMapping("/getorderlist.do")
    public Result<List<Order>> getOrderList(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return orderService.getOrderListByUserId(userId.asInt());
    }

    @GetMapping("/cancelorder.do/{orderNum}")
    public int cancelOrder(HttpServletRequest request,@PathVariable("orderNum")String orderNum){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return orderService.cancelOrder(userId.asInt(),orderNum);
    }

    @PostMapping("/createviporder.do")
    public Result<String> createVipOrder(HttpServletRequest request, @RequestParam int vipPackageId){
        return orderService.createVipOrder(request,vipPackageId);
    }

    @GetMapping("/renewvippackage.do")
    public Result<String> renewVipPackage(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return orderService.renewVipPackage(userId.asInt());
    }
}
