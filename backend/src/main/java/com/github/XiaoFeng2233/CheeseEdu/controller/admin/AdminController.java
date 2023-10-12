package com.github.XiaoFeng2233.CheeseEdu.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;
import com.github.XiaoFeng2233.CheeseEdu.service.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/do/admin")
public class AdminController {

    @Autowired
    private LogService logService;
    @Autowired
    private VipPackageService vipPackageService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionReplyService questionReplyService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/getlog.do")
    public Result<IPage<Log>> getLog(@RequestParam Integer userId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return logService.getLogByUserId(userId, pageNumber, pageSize);
    }

    @PostMapping("/addvippackage.do")
    public Result<String> addVipPackage(@RequestBody VipPackage vipPackage) {
        return vipPackageService.addVipPackage(vipPackage);
    }

    @GetMapping("/getallvippackage.do")
    public Result<List<VipPackage>> getAllVipPackage() {
        return vipPackageService.getAllVipPackage();
    }

    @GetMapping("/deletevippackage.do/{id}")
    public Result<String> deleteVipPackaget(@PathVariable("id")int id){
        return vipPackageService.deleteVipPackage(id);
    }

    @GetMapping("/getvippackagebyid.do/{id}")
    public Result<VipPackage> getVipPackageById(@PathVariable("id")int id){
        return vipPackageService.getVipPackageById(id);
    }

    @PostMapping("/updatevippackagebyid.do")
    public Result<String> updateVipPackageById(@RequestBody VipPackage vipPackage){
        return vipPackageService.updateVipPackageById(vipPackage);
    }

    @GetMapping("/getarticlelist.do")
    public Result<List<Article>> getArticleList() {
        return articleService.getArticleList();
    }

    @PostMapping("/addarticle.do")
    public Result<String> addArticle(@RequestBody Article article) {
        return articleService.addArticle(article);
    }

    @GetMapping("/getarticlebyid.do/{id}")
    public Result<Article> getArticleById(@PathVariable("id") int id) {
        return articleService.getArticleById(id);
    }

    @PostMapping("/updatearticlebyid.do")
    public Result<String> updateArticleById(@RequestBody Article article) {
        return articleService.updateArticleById(article);
    }

    @GetMapping("/deletearticlebyid.do/{id}")
    public Result<String> deleteArticleById(@PathVariable("id") int id) {
        return articleService.deleteArticleById(id);
    }

    @GetMapping("/getalluser.do/{pagenumber}/{pagesize}")
    public Result<IPage<Users>> getAllUser(@PathVariable("pagenumber") int pagenumber, @PathVariable("pagesize") int pagesize) {
        return userService.getAllUser(pagenumber,pagesize);
    }

    @GetMapping("/getuserbyusername.do/{param}")
    public Result<List<Users>> getUserByUserName(@PathVariable("param")String param){
        return userService.getUserByUserNameOrMobileNumber(param);
    }

    @PostMapping("/adduser.do")
    public Result<String> addUser(@RequestBody Users user, HttpServletRequest request){
        return userService.addUser(user,request);
    }

    @GetMapping("/getvipusers.do/{pagenumber}/{pagesize}")
    public Result<IPage<Users>> getVipUsers(@PathVariable("pagenumber") int pagenumber,@PathVariable("pagesize") int pagesize){
        return userService.getVipUsers(pagenumber,pagesize);
    }

    @GetMapping("/getvipusersbyusernameormobilenumber.do/{param}")
    public Result<List<Users>> getVipUsersByUsernameOrMobileNumber(@PathVariable("param")String param){
        return userService.getVipUsersByUserNameOrMobileNumber(param);
    }

    @PostMapping("/getallquestion.do")
    public Result<IPage<Question>> getAllQuestion(@RequestParam("count") int count,@RequestParam("page") int page){
        return questionService.getAllQuestion(count,page);
    }

    @GetMapping("/getquestionbyid.do/{id}")
    public Result<Question> getQuestionById(@PathVariable("id") int id){
        return questionService.getQuestionByIdAdmin(id);
    }

    @GetMapping("/getallreplybyquestionid.do/{id}")
    public Result<List<QuestionReply>> getAllReplyByQuestionId(@PathVariable("id") int questionId){
        return questionReplyService.getQuestionReplyByQuestionId(questionId);
    }

    @GetMapping("/updatequestionsolved.do/{id}")
    public Result<String> updateQuestionSolved(@PathVariable("id") int id){
        return questionService.updateQuestionSolved(id);
    }

    @GetMapping("/deleteQuestion.do/{id}")
    public Result<String> deleteQuestion(@PathVariable("id") int id){
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/deletequestionreply.do/{id}")
    public Result<String> deleteQuestionReply(@PathVariable("id") int id){
        return questionReplyService.deleteQuestionReply(id);
    }

    @GetMapping("/statistics.do")
    public Result<HashMap<String,Object>> statistics(){
        BigDecimal todayOrderAmount = orderService.getTodayOrderAmount();
        Integer todayOrderCount = orderService.getTodayOrderCount();
        Integer todayNewUsersCount = userService.getTodayNewUsersCount();
        Integer todayNewQuestionCount = questionService.getTodayNewQuestionCount();
        HashMap<String,Object> map = new HashMap<>();
        map.put("todayOrderAmount",todayOrderAmount);
        map.put("todayOrderCount",todayOrderCount);
        map.put("todayNewUsersCount",todayNewUsersCount);
        map.put("todayNewQuestionCount",todayNewQuestionCount);
        return new Result<HashMap<String,Object>>().success(map);
    }

    @GetMapping("/getnosolvedquestion.do/{limit}")
    public Result<List<Question>> getNoSolvedQuestion(@PathVariable("limit")int limit){
        return questionService.getQuestionWithNoSolved(limit);
    }

    @PostMapping("/getorder.do")
    public Result<IPage<Order>> getOrder(@RequestParam("page") int page,@RequestParam("count") int count){
        return orderService.getOrder(page,count);
    }

    @GetMapping("/getorderbyid.do/{orderId}")
    public Result<Order> getOrderById(@PathVariable("orderId")int orderId){
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/getorderbyordernum.do/{orderNum}")
    public Result<Order> getOrderByOrderNum(@PathVariable("orderNum")String orderNum){
        return orderService.getOrderByOrderNum(orderNum);
    }

    @GetMapping("/getuserbyid.do/{id}")
    public Result<Users> getUserById(@PathVariable("id") int id){
        return userService.getUserByIdA(id);
    }

    @PostMapping("/updateuser.do")
    public Result<String> updateUser(@RequestBody Users user){
        userService.updateUserById(user.getId(),user);
        return new Result<String>().success("用户信息修改成功");
    }

    @PostMapping("/updateuservip.do")
    public Result<String> updateUserVip(@RequestParam("id")int userid, @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam("expireTime") String expireTime, @RequestParam("vipPackageId")int vipPackageId) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = simpleDateFormat.parse(expireTime);
        return userService.updateUserVip(userid,time,vipPackageId);
    }

    @GetMapping("/deleteuserbyid.do/{id}")
    public Result<String> deleteUserById(@PathVariable("id")int id){
        return userService.deleteUserById(id);
    }
}
