package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.XiaoFeng2233.CheeseEdu.dao.VideoMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.Video;
import com.github.XiaoFeng2233.CheeseEdu.service.VideoService;
import com.github.XiaoFeng2233.CheeseEdu.util.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private StorageUtil storageUtil;

    @Override
    public Result<List<Video>> getVideosByCourseId(int id) {
        List<Video> videos = videoMapper.selectVideosByCourseId(id);
        return new Result<List<Video>>().success(videos);
    }

    @Override
    public Result<Video> getVideoById(int id) {
        Video video = videoMapper.selectById(id);
        return new Result<Video>().success(video);
    }

    @Override
    public Result<String> updateVideoById(int id,  int chapterId,  String title) {
        int i = videoMapper.updateVideoById(id,title,chapterId,new Date());
        if (i > 0) {
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,发生未知错误");
    }

    @Override
    public Result<Video> getVideoUploadUrl(Video video) {
        String[] split = video.getFileName().split("\\.");
        String suffix = split[split.length-1];
        video.setCreatedTime(new Date());
        video.setUpdatedTime(new Date());
        video.setStatus(0);
        video.setIsDelete(0);
        StringBuilder objName = new StringBuilder();
        objName.append("videos");
        objName.append("/");
        objName.append(DateUtil.format(new Date(),"yyyy-MM-dd"));
        objName.append("/");
        objName.append(IdUtil.simpleUUID());
        objName.append(".");
        objName.append(suffix);
        video.setObjectName(objName.toString());
        Result<URL> videoUploadUrl = storageUtil.getVideoUploadUrl(objName.toString());
        int i = videoMapper.insert(video);
        if (i>0){
            video.setUrl(videoUploadUrl.getData());
            return new Result<Video>().success(video);
        }
        return new Result<Video>().error(null);
    }

    @Override
    public void videoUploadSuccess(String objName) {
        videoMapper.updateStatusByObjectName(objName);
    }

    @Override
    public Result<List<Video>> getVideoByChapterId(int id) {
        List<Video> videos = videoMapper.selectVideoByChapterId(id);
        return new Result<List<Video>>().success(videos);
    }

    @Override
    public Result<String> deleteVideoById(int id) {
        videoMapper.deleteVideoById(id);
        return new Result<String>().success("视频删除成功");
    }
}
