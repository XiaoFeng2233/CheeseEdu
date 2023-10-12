package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.XiaoFeng2233.CheeseEdu.dao.LogMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Log;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Result<IPage<Log>> getLogByUserId(Integer userId, Integer pageNumber, Integer pageSize) {
        Page<Log> page = new Page<>(pageNumber,pageSize);
        IPage<Log> iPage = logMapper.getLogByUserId(page,userId);
        return new Result<IPage<Log>>().success(iPage);
    }

    @Override
    public Result<List<Log>> getLogByUserId(Integer userId, Integer count) {
        List<Log> logs = logMapper.getLogByUserIdLimited(userId, count);
        return new Result<List<Log>>().success(logs);
    }
}
