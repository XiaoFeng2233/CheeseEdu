package com.github.XiaoFeng2233.CheeseEdu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.Question;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionService {
    Result<IPage<Question>> getQuestionByCondition(int page,int count,int sort);
    Result<Question> getQuestionById(int id);
    Result<List<Question>> getQuestionByUserId(int userId);
    Result<List<Question>> getQuestionByUserIdWithLimit(int userId,int count);
    Result<List<Question>> getTodayQuestionList(int count);
    Result<String> createQuestion(int userId,String title,String content);
    Result<String> createQuestionWithImage(int userId, String title, String content, MultipartFile[] file);
    Result<List<Question>> getAllQuestionList(int userId);
    Result<IPage<Question>> getAllQuestion(int count,int page);
    Result<Question> getQuestionByIdAdmin(int id);
    Result<String> updateQuestionSolved(int id);
    Result<String> deleteQuestion(int id);
    List<Question> searchQuestion(String keyword);
    Integer getTodayNewQuestionCount();
    Result<List<Question>> getQuestionWithNoSolved(int limit);
    int increaseViewCountById(int id,int count);
}
