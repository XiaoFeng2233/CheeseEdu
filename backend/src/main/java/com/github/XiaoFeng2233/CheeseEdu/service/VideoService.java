package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Video;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URL;
import java.util.List;

public interface VideoService {
    Result<List<Video>> getVideosByCourseId(int id);
    Result<Video> getVideoById(int id);
    Result<String> updateVideoById( int id,  int chapterId,  String title);
    Result<Video> getVideoUploadUrl(Video video);
    void videoUploadSuccess(String objName);
    Result<List<Video>> getVideoByChapterId(int id);
    Result<String> deleteVideoById(int id);
}
