package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.CourseRecordsMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.CourseRecords;
import com.github.XiaoFeng2233.CheeseEdu.service.CourseRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseRecordsServiceImpl implements CourseRecordsService {
    @Autowired
    private CourseRecordsMapper courseRecordsMapper;
    public int insertWatchRecord(CourseRecords courseRecords){
        return courseRecordsMapper.insertWatchRecord(courseRecords.getUserId(),courseRecords.getCourseId(),courseRecords.getVideoId(),courseRecords.getCreatedTime());
    }
}
