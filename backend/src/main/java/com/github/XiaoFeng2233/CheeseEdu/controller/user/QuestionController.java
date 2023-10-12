package com.github.XiaoFeng2233.CheeseEdu.controller.user;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.interfaces.Claim;
import com.github.XiaoFeng2233.CheeseEdu.entity.Question;
import com.github.XiaoFeng2233.CheeseEdu.entity.QuestionReply;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.QuestionReplyService;
import com.github.XiaoFeng2233.CheeseEdu.service.QuestionService;
import com.github.XiaoFeng2233.CheeseEdu.util.JWTUtil;
import com.github.XiaoFeng2233.CheeseEdu.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/do/question")
public class QuestionController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionReplyService questionReplyService;

    @PostMapping("/replyquestion.do")
    public Result<String> replyQuestion(HttpServletRequest request, @RequestParam String content, @RequestParam int questionId){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        QuestionReply reply = new QuestionReply();
        reply.setCreatedTime(new Date());
        reply.setStatus(1);
        reply.setUserId(userId.asInt());
        reply.setContent(content);
        reply.setQuestionId(questionId);
        reply.setImages("[]");
        return questionReplyService.addQuestionReply(reply);
    }

    @PostMapping("/replyquestionwithimages.do")
    public Result<String> replyQuestionWithImages(HttpServletRequest request,@RequestParam("files")MultipartFile[] files,@RequestParam String content,@RequestParam int questionId) throws Exception{
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        JSONArray jsonArray = new JSONArray();
        if (files == null || files.length <= 0){
            return new Result<String>().error("请上传文件");
        }
        for (MultipartFile file : files) {
            MyUtils myUtils = new MyUtils();
            String  s  = myUtils.uploadImage(file);
            if (s==null){
                return new Result<String>().error("未被允许的文件格式");
            }
            jsonArray.add(s);
        }

        QuestionReply reply = new QuestionReply();
        reply.setQuestionId(questionId);
        reply.setImages(jsonArray.toJSONString());
        reply.setStatus(1);
        reply.setContent(content);
        reply.setUserId(userId.asInt());
        reply.setCreatedTime(new Date());
        return questionReplyService.addQuestionReply(reply);
    }

    @GetMapping("/getquestionlist.do/{count}")
    public Result<List<Question>> getQuestioListWithLimit(HttpServletRequest request,@PathVariable int count){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return questionService.getQuestionByUserIdWithLimit(userId.asInt(),count);
    }

    @GetMapping("/getquestionlist.do")
    public Result<List<Question>> getQuestionList(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return questionService.getQuestionByUserId(userId.asInt());
    }


    @PostMapping("/createquestion.do")
    public Result<String> createQuestion(HttpServletRequest request,@RequestParam String title,@RequestParam String content){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return questionService.createQuestion(userId.asInt(),title,content);
    }

    @PostMapping("/createquestionwithimage.do")
    public Result<String> createQuestionWithImage(HttpServletRequest request,@RequestParam("file") MultipartFile[] file,@RequestParam String title,@RequestParam String content){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return questionService.createQuestionWithImage(userId.asInt(),title,content,file);
    }

    @GetMapping("/getallquestionlist.do")
    public Result<List<Question>> getAlLQuestionList(HttpServletRequest request){
        String token = request.getHeader("token");
        Claim userId = jwtUtil.getClaimByName(token, "userId");
        return questionService.getAllQuestionList(userId.asInt());
    }

}