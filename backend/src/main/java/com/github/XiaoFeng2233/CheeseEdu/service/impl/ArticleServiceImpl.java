package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.dao.ArticleMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Article;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper mapper;

    @Override
    public Result<String> addArticle(Article article) {
        article.setCreatedTime(new Date());
        article.setUpdatedTime(new Date());
        article.setViewNumbers(0);
        int i = mapper.insert(article);
        if (i > 0) {
            return new Result<String>().success("添加成功");
        }
        return new Result<String>().error("添加失败,发生未知错误");
    }

    @Override
    public Result<List<Article>> getArticleList() {
        List<Article> articleList =  mapper.selectArticleList();
        return new Result<List<Article>>().success(articleList);
    }

    @Override
    public Result<Article> getArticleById(int id) {
        Article article = mapper.selectArticleById(id);
        return new Result<Article>().success(article);
    }

    @Override
    public Result<String> updateArticleById(Article article) {
        article.setUpdatedTime(new Date());
        int i = mapper.updateById(article);
        if (i>0){
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,发生未知错误");
    }

    @Override
    public Result<String> deleteArticleById(int id) {
        int i = mapper.deleteById(id);
        if (i > 0) {
            return new Result<String>().success("删除成功");
        }
        return new Result<String>().error("删除失败,发生未知错误");
    }

    @Override
    @Cacheable(value = {"article"},key = "#count")
    public Result<List<Article>> getArticleByViewNumber(int count) {
        List<Article> articles = mapper.selectArticleOrderByViewNumber(count);
        return new Result<List<Article>>().success(articles);
    }

    @Override
    public Result<IPage<Article>> getAllArticle(int pagenumber, int pagesize) {
        Page<Article> page = new Page<>(pagenumber,pagesize);
        IPage<Article> articleIPage = mapper.selectAllArticle(page);
        return new Result<IPage<Article>>().success(articleIPage);
    }

    @Override
    public List<Article> searchArticle(String keyword) {
        return mapper.searchArticle(keyword);
    }

    @Override
    public int increaseViewCountById(int id, int count) {
        return mapper.increaseViewCountById(id,count);
    }
}
