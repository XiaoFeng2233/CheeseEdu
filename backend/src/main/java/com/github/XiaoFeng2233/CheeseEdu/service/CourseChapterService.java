package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.CourseChapter;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import java.util.List;

public interface CourseChapterService {
    Result<List<CourseChapter>> getChapterByCourseId(int id);
    Result<String> addChapter(CourseChapter courseChapter);
    Result<CourseChapter> getChapterById(int id);

    Result<String> updateChapterById(CourseChapter courseChapter);

    Result<String> deleteChapterById(int id);
}
