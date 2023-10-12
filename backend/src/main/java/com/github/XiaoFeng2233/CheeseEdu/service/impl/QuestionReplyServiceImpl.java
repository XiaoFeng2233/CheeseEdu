package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.QuestionReplyMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.QuestionReply;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.QuestionReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionReplyServiceImpl implements QuestionReplyService {
    @Autowired
    private QuestionReplyMapper mapper;
    @Override
    public Result<List<QuestionReply>> getQuestionReplyByQuestionId(int questionId) {
        List<QuestionReply> questionReplies = mapper.selectQuestionReplyByQuestionId(questionId);
        return new Result<List<QuestionReply>>().success(questionReplies);
    }

    @Override
    public Result<String> addQuestionReply(QuestionReply questionReply) {
        mapper.insert(questionReply);
        return new Result<String>().success("回复成功");
    }

    @Override
    public Result<String> deleteQuestionReply(int id) {
        int i = mapper.deleteQuestionReplyById(id);
        return new Result<String>().success("删除成功");
    }
}
