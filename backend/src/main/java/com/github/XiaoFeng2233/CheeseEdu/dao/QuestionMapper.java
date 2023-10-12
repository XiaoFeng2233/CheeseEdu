package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.entity.Course;
import com.github.XiaoFeng2233.CheeseEdu.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper extends BaseMapper<Question> {
    IPage<Question> getQuestionByCondition(Page<Question> page, @Param("sort")Integer sort);

    @Select("select *,(select avatar from users where id = question.user_id) as user_avatar,(select nickname from users where id = question.user_id) as user_nickname from question where id = #{id} and status = 1")
    Question selectQuestionById(int id);

    @Select("select *,(select nickname from users where id = question.user_id) as user_nick_name,(select avatar from users where id = question.user_id) as user_avatar from question where user_id = #{userId} and status = 1")
    List<Question> selectQuestionByUserId(int userId);

    @Select("select *,(select nickname from users where id = question.user_id) as user_nick_name,(select avatar from users where id = question.user_id) as user_avatar from question where user_id = #{userId} and status = 1 limit #{count}")
    List<Question> selectQuestionByUserIdLimit(int userId,int count);

    @Select("select *,(select nickname from users where id = question.user_id) as user_nick_name,(select avatar from users where id = question.user_id) as user_avatar from question where TO_DAYS(created_time) = TO_DAYS(CURDATE()) and status = 1 limit #{count}")
    List<Question> selectTodayQuestionWithLimit(int count);

    @Select("select * from question where user_id = #{userId} and status = 1")
    List<Question> selectAllQuestionByUserId(int userId);

    @Select("select *,(select username from users where id = question.user_id) as user_name ," +
            "(select count(*) from question_reply where question_id = question.id and status = 1) as answer_count " +
            "from question where status = 1 order by created_time desc")
    IPage<Question> selectAllQuestion(Page<Question> page);

    @Select("select *,(select username from users where id = question.user_id) as user_name ," +
            "(select count(*) from question_reply where question_id = question.id and status = 1) as answer_count " +
            "from question where status = 1 and id = #{id}")
    Question selectQuestionByIdAdmin(int id);

    @Update("update question set is_solved = 1 where id = #{id}")
    int updateQuestionSolvedById(int id);

    @Update("update question set status = 0 where id = #{id}")
    int deleteQuestionById(int id);

    @Select("select * from question where title like CONCAT('%',#{keyword},'%') or content like CONCAT('%',#{keyword},'%') and status = 1")
    List<Question> searchQuestion(String keyword);

    @Select("select count(*) from question where TO_DAYS(created_time) = TO_DAYS(CURDATE())")
    Integer selectTodayNewQuestionCount();

    @Select("select * from question where is_solved = 0 limit #{limit}")
    List<Question> selectQuestionWithNoSolved(int limit);

    @Update("update question set view_count = view_count + #{count} where id = #{id}")
    int increaseViewCountById(int id,int count);

}
