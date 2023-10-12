package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.QuestionReply;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import java.util.List;

public interface QuestionReplyService {
    Result<List<QuestionReply>> getQuestionReplyByQuestionId(int questionId);

    Result<String> addQuestionReply(QuestionReply questionReply);

    Result<String> deleteQuestionReply(int id);
}
