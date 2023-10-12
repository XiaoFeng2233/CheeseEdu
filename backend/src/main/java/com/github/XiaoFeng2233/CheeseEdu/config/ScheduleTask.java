package com.github.XiaoFeng2233.CheeseEdu.config;

import com.github.XiaoFeng2233.CheeseEdu.entity.Question;
import com.github.XiaoFeng2233.CheeseEdu.service.ArticleService;
import com.github.XiaoFeng2233.CheeseEdu.service.QuestionService;
import com.github.XiaoFeng2233.CheeseEdu.service.UserService;
import com.github.XiaoFeng2233.CheeseEdu.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleTask {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/30 * * * ? ")
    public void checkExpiredVip(){
        Integer i = userService.updateUserVipExpired();
        log.info("校准了" + i + "条会员信息");
    }

    @Scheduled(cron = "0 0/10 * * * ? ")
    private void incrQuestionViewCount(){
        Set<String> keys = redisUtil.keys("question-incr:*");
        keys.forEach((e)->{
            Integer id = Integer.parseInt(e.split(":")[1]);
            Object o = redisUtil.get(e);
            if (o!=null){
                int count = (Integer) o;
                questionService.increaseViewCountById(id,count);
                redisUtil.delete(e);
            }
        });
    }
    @Scheduled(cron = "0 0/10 * * * ? ")
    private void incrArticleViewCount(){
        Set<String> keys = redisUtil.keys("article-incr:*");
        keys.forEach((e)->{
            Integer id = Integer.parseInt(e.split(":")[1]);
            Object o = redisUtil.get(e);
            if (o!=null){
                int count = (Integer) o;
                articleService.increaseViewCountById(id,count);
                redisUtil.delete(e);
            }
        });
    }
}
