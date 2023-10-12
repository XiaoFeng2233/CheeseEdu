package com.github.XiaoFeng2233.CheeseEdu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.Article;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import java.util.List;

public interface ArticleService {
    Result<String> addArticle(Article article);

    Result<List<Article>> getArticleList();

    Result<Article> getArticleById(int id);

    Result<String> updateArticleById(Article article);

    Result<String> deleteArticleById(int id);

    Result<List<Article>> getArticleByViewNumber(int count);

    Result<IPage<Article>> getAllArticle(int pagenumber,int pagesize);

    List<Article> searchArticle(String keyword);

    int increaseViewCountById(int id,int count);
}
