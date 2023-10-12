package com.github.XiaoFeng2233.CheeseEdu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select("select * from article")
    List<Article> selectArticleList();

    @Select("select * from article where id = #{id}")
    Article selectArticleById(int id);

    @Select("select * from article order by view_numbers desc limit #{count}")
    List<Article> selectArticleOrderByViewNumber(int count);

    @Select("select * from article order by created_time desc")
    IPage<Article> selectAllArticle(Page<?> page);

    @Select("select * from article where title like CONCAT('%',#{keyword},'%') or content like CONCAT('%',#{keyword},'%')")
    List<Article> searchArticle(String keyword);

    @Update("update article set view_numbers = view_numbers + #{count} where id = #{id}")
    int increaseViewCountById(int id,int count);
}
