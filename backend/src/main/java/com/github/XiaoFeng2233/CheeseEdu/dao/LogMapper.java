package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LogMapper extends BaseMapper<Log> {
    @Select("select *,(select username from users where id = user_id) as user_name from log where user_id = #{userId} ")
    IPage<Log> getLogByUserId(Page<?> page,Integer userId);

    @Select("select * from log where user_id = #{userId}  order by created_time desc limit #{count}")
    List<Log> getLogByUserIdLimited(Integer userId,Integer count);

}
