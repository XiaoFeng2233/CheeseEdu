package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.QuestionReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionReplyMapper extends BaseMapper<QuestionReply> {
    @Select("select *,(select nickname from users where id = user_id) as user_nick_name,(select avatar from users where id = user_id) as avatar,(select username from users where id = question_reply.user_id) as user_name from question_reply where question_id = #{id} and status = 1")
    List<QuestionReply> selectQuestionReplyByQuestionId(int id);

    @Update("update question_reply set status = 0 where id = #{id};")
    int deleteQuestionReplyById(int id);
}
