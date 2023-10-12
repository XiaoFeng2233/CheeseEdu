package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.dao.QuestionMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Course;
import com.github.XiaoFeng2233.CheeseEdu.entity.Question;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.QuestionReplyService;
import com.github.XiaoFeng2233.CheeseEdu.service.QuestionService;
import com.github.XiaoFeng2233.CheeseEdu.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionReplyService questionReplyService;

    @Override
    public Result<IPage<Question>> getQuestionByCondition(int page,int count, int sort) {
        Page<Question> p = new Page<>(page, count);
        IPage<Question> ipage = questionMapper.getQuestionByCondition(p,sort);
        return new Result<IPage<Question>>().success(ipage);
    }

    @Override
    public Result<Question> getQuestionById(int id) {
        Question question = questionMapper.selectQuestionById(id);
        return new Result<Question>().success(question);
    }

    @Override
    public Result<List<Question>> getQuestionByUserId(int userId) {
        List<Question> questions = questionMapper.selectQuestionByUserId(userId);
        return new Result<List<Question>>().success(questions);
    }

    @Override
    public Result<List<Question>> getQuestionByUserIdWithLimit(int userId,int count) {
        List<Question> questions = questionMapper.selectQuestionByUserIdLimit(userId, count);
        return new Result<List<Question>>().success(questions);
    }

    @Override
    public Result<List<Question>> getTodayQuestionList(int count) {
        List<Question> questions = questionMapper.selectTodayQuestionWithLimit(count);
        return new Result<List<Question>>().success(questions);
    }

    @Override
    public Result<String> createQuestion(int userId, String title, String content) {
        Question question = new Question();
        question.setContent(content);
        question.setTitle(title);
        question.setCreatedTime(new Date());
        question.setUserId(userId);
        question.setIsSolved(0);
        question.setStatus(1);
        question.setImages("[]");
        question.setViewCount(0);
        questionMapper.insert(question);
        return new Result<String>().success("问题发布成功");
    }


    @Override
    public Result<String> createQuestionWithImage(int userId, String title, String content, MultipartFile[] files) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (MultipartFile file : files) {
                MyUtils myUtils = new MyUtils();
                String  s  = myUtils.uploadImage(file);
                if (s==null){
                    return new Result<String>().error("未被允许的文件格式");
                }
                jsonArray.add(s);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result<String>().error("图片上传失败");
        }

        Question question = new Question();
        question.setContent(content);
        question.setTitle(title);
        question.setCreatedTime(new Date());
        question.setUserId(userId);
        question.setIsSolved(0);
        question.setStatus(1);
        question.setImages(jsonArray.toJSONString());
        question.setViewCount(0);
        questionMapper.insert(question);
        return new Result<String>().success("问题发布成功");
    }

    @Override
    public Result<List<Question>> getAllQuestionList(int userId) {
        return new Result<List<Question>>().success(questionMapper.selectAllQuestionByUserId(userId));
    }

    @Override
    public Result<IPage<Question>> getAllQuestion(int count, int page) {
        Page<Question> p = new Page<>(page,count);
        IPage<Question> iPage = questionMapper.selectAllQuestion(p);
        return new Result<IPage<Question>>().success(iPage);
    }

    @Override
    public Result<Question> getQuestionByIdAdmin(int id) {
        Question question = questionMapper.selectQuestionByIdAdmin(id);
        return new Result<Question>().success(question);
    }

    @Override
    public Result<String> updateQuestionSolved(int id) {
        int i = questionMapper.updateQuestionSolvedById(id);
        return new Result<String>().success("修改成功");
    }

    @Override
    public Result<String> deleteQuestion(int id) {
        questionMapper.deleteQuestionById(id);
        return new Result<String>().success("删除成功");
    }

    @Override
    public List<Question> searchQuestion(String keyword) {
        return questionMapper.searchQuestion(keyword);
    }

    @Override
    public Integer getTodayNewQuestionCount() {
        return questionMapper.selectTodayNewQuestionCount();
    }

    @Override
    public Result<List<Question>> getQuestionWithNoSolved(int limit) {
        List<Question> questions = questionMapper.selectQuestionWithNoSolved(limit);
        return new Result<List<Question>>().success(questions);
    }

    @Override
    public int increaseViewCountById(int id, int count) {
        return questionMapper.increaseViewCountById(id,count);
    }
}
