package com.github.XiaoFeng2233.CheeseEdu.controller.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;
import com.github.XiaoFeng2233.CheeseEdu.service.*;
import com.github.XiaoFeng2233.CheeseEdu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/do/common")
public class CommonController {
    @Autowired
    private GlobalConfigService globalConfigService;
    @Autowired
    private BannerConfigService bannerConfigService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionReplyService questionReplyService;
    @Autowired
    private CourseCommentService courseCommentService;
    @Autowired
    private VipPackageService vipPackageService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/getagreement.do")
    public Result<String> getAgreement(){
        String siteAgreement = globalConfigService.getConfig().getData().getSiteAgreement();
        return new Result<String>().success(siteAgreement);
    }

    @GetMapping("/getbaseconfig.do")
    public Result<HashMap<String,Object>> getBaseConfig(){
        return globalConfigService.getBaseConfig();
    }

    @GetMapping("/getpcbanner.do")
    public Result<List<BannerConfig>> getPcBanner(){
        return bannerConfigService.getPcBannerList();
    }
    @GetMapping("/getmobilebanner.do")
    public Result<List<BannerConfig>> getMobileBanner(){
        return bannerConfigService.getMobileBannerList();
    }

    @GetMapping("/getfreecourse.do")
    public Result<List<Course>> getFreeCourse(){
        return courseService.getFreeCourse();
    }

    @GetMapping("/getlatestcourse.do")
    public Result<List<Course>> getLatestCourse(){
        return courseService.getLatestCourse();
    }

    @GetMapping("/getallcategory.do")
    public Result<List<CourseCategory>> getAllCategory(){
        return courseCategoryService.getAllCourseCategoryForSale();
    }

    @PostMapping("/getcoursebycondition.do")
    public Result<IPage<Course>> getCourseByCondition(@RequestParam int page, @RequestParam int categoryId , @RequestParam int sort){
        return courseService.getCourseByCondition(page,categoryId,sort);
    }

    @GetMapping("/getrandomcourse.do/{number}")
    public Result<List<Course>> getRandomCourse(@PathVariable int number){
        return courseService.getRandomCourse(number);
    }

    @GetMapping("/getcoursebyid.do/{id}")
    public Result<Course> getCourseById(@PathVariable int id){
        return courseService.getActiveCourseById(id);
    }

    @GetMapping("/getarticlebyviewnumber.do/{count}")
    public Result<List<Article>> getArticleByViewNumber(@PathVariable("count") int count){
        return articleService.getArticleByViewNumber(count);
    }

    @GetMapping("/getallarticle.do/{pagenumber}/{pagesize}")
    public Result<IPage<Article>> getAllArticle(@PathVariable("pagenumber") int pagenumber,@PathVariable("pagesize")int pagesize){
        return articleService.getAllArticle(pagenumber,pagesize);
    }

    @GetMapping("/getarticlebyid.do/{articleid}")
    public Result<Article> getArticleById(@PathVariable("articleid") int id){
        redisUtil.incrBy("article-incr:" + id,1);
        return articleService.getArticleById(id);
    }


    @GetMapping("/getquestionlistbycondition.do/{page}/{count}/{sort}")
    public Result<IPage<Question>> getQuestionListByCondition(@PathVariable int page,@PathVariable int count,@PathVariable int sort){
        return questionService.getQuestionByCondition(page,count,sort);
    }

    @GetMapping("/gettodayquestion.do/{count}")
    public Result<List<Question>> getTodayQuestion(@PathVariable int count){
        return questionService.getTodayQuestionList(count);
    }

    @GetMapping("/getquestion.do/{id}")
    public Result<Question> getQuestion(@PathVariable int id){
        redisUtil.incrBy("question-incr:" + id,1);
        return questionService.getQuestionById(id);
    }

    @GetMapping("/getquestionreply.do/{id}")
    public Result<List<QuestionReply>> getQuestionReplyByQuestionId(@PathVariable int id){
        return questionReplyService.getQuestionReplyByQuestionId(id);
    }

    @GetMapping("/getcoursecomment.do/{courseId}")
    public Result<List<CourseComment>> getCourseComment(@PathVariable("courseId")Integer courseId){
        return courseCommentService.getCourseCommentByCourseId(courseId);
    }

    @GetMapping("/getcoursechapter.do/{courseId}")
    public Result<List<CourseChapter>> getCourseChapter(@PathVariable("courseId")int courseId){
        return courseService.getCourseChapter(courseId);
    }

    @PostMapping("/search.do")
    public Result<HashMap<String,Object>> search(@RequestParam("keyword")String keyword){
        List<Article> articles = articleService.searchArticle(keyword);
        List<Question> questions = questionService.searchQuestion(keyword);
        List<Course> courses = courseService.searchCourse(keyword);
        HashMap<String,Object> map = new HashMap<>();
        map.put("article",articles);
        map.put("question",questions);
        map.put("course",courses);
        return new Result<HashMap<String,Object>>().success(map);
    }
    @GetMapping("/getvippackgelist.do")
    public Result<List<VipPackage>> getVipPackageList(){
        return vipPackageService.getAllVipPackage();
    }

}
