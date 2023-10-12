package com.github.XiaoFeng2233.CheeseEdu.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;
import com.github.XiaoFeng2233.CheeseEdu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/do/admin/course")
public class CourseController {

    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseChapterService courseChapterService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private CourseCommentService courseCommentService;

    @GetMapping("/getallcoursecategory.do")
    public Result<List<CourseCategory>> getAllCourseCategory() {
        return courseCategoryService.getAllCourseCategory();
    }

    @GetMapping("/getallcoursecategoryforsale.do")
    public Result<List<CourseCategory>> getAllCourseCategoryForSale(){
        return courseCategoryService.getAllCourseCategoryForSale();
    }

    @GetMapping("/getcoursecategory.do/{id}")
    public Result<CourseCategory> getCourseCategory(@PathVariable Integer id) {
        return courseCategoryService.getCourseCategoryById(id);
    }

    @PostMapping("/updatecoursecategory.do")
    public Result<String> updateCourseCategory(@RequestBody CourseCategory courseCategory) {
        return courseCategoryService.updateCourseCategory(courseCategory);
    }

    @PostMapping("/addcoursecategory.do")
    public Result<String> addCourseCategory(@RequestBody CourseCategory courseCategory) {
        return courseCategoryService.addCourseCategory(courseCategory);
    }

    @GetMapping("/getallcourse.do")
    public Result<List<Course>> getAllCourse() {
        return courseService.getAllCourse();
    }

    @PostMapping("/addcourse.do")
    public Result<String> addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/getcoursebyid.do/{id}")
    public Result<Course> getCourseById(@PathVariable Integer id){
        return courseService.getCourseById(id);
    }

    @PostMapping("/updatecourse.do")
    public Result<String> updateCourse(@RequestBody Course course){
        return courseService.updateCourse(course);
    }

    @GetMapping("/getchapterbycourseid.do/{id}")
    public Result<List<CourseChapter>> getChapterByCourseId(@PathVariable("id") int id){
        return courseChapterService.getChapterByCourseId(id);
    }

    @PostMapping("/addchapter.do")
    public Result<String> addChapter(@RequestBody CourseChapter chapter){
        return courseChapterService.addChapter(chapter);
    }

    @GetMapping("/getchapterbyid.do/{id}")
    public Result<CourseChapter> getChapterById(@PathVariable("id") int id){
        return courseChapterService.getChapterById(id);
    }

    @PostMapping("/updatechapterbyid.do")
    public Result<String> updateChapterById(@RequestBody CourseChapter courseChapter){
        return courseChapterService.updateChapterById(courseChapter);
    }

    @GetMapping("/getvideosbycourseid.do/{id}")
    public Result<List<Video>> getVideosByCourseId(@PathVariable("id") int id){
        return videoService.getVideosByCourseId(id);
    }

    @GetMapping("/getvideobyid.do/{id}")
    public Result<Video> getVideoById(@PathVariable("id")int id){
        return videoService.getVideoById(id);
    }

    @PostMapping("/updatevideobyid.do")
    public Result<String> updateVideoById(@RequestParam int id,@RequestParam int chapterId,@RequestParam String title){
        return videoService.updateVideoById(id,chapterId,title);
    }

    @PostMapping("/getvideouploadurl.do")
    public Result<Video> getVideoUpLoadUrl(@RequestBody Video video){
        return videoService.getVideoUploadUrl(video);
    }

    @PostMapping("/videouploadsucess.do")
    public void videoUploadSuccess(@RequestParam String objName){
        videoService.videoUploadSuccess(objName);
    }

    @PostMapping("/getcoursecommentlist.do")
    public Result<IPage<CourseComment>> getCourseCommentList(@RequestParam("page") int page,@RequestParam("count") int count){
        return courseCommentService.getCourseCommentList(page,count);
    }

    @GetMapping("/deletecommentbyid.do/{id}")
    public Result<String> deleteCommentById(@PathVariable("id")int id){
        return courseCommentService.deleteCommentById(id);
    }

    @GetMapping("/deletevideo.do/{id}")
    public Result<String> deleteVideoById(@PathVariable("id")int id){
        return videoService.deleteVideoById(id);
    }

    @GetMapping("/deletecourse.do{id}")
    public Result<String> deleteCourseById(@PathVariable("id")int id){
        return courseService.deleteCourseById(id);
    }

    @GetMapping("/deletechapter.do/{id}")
    public Result<String> deleteChapterById(@PathVariable("id")int id){
        return courseChapterService.deleteChapterById(id);
    }

    @GetMapping("/deletecategory.do/{id}")
    public Result<String> deleteCourseCategoryById(@PathVariable("id")int id){
        return courseCategoryService.deleteCourseCategoryById(id);
    }
}
