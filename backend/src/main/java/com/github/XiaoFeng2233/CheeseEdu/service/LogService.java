package com.github.XiaoFeng2233.CheeseEdu.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.XiaoFeng2233.CheeseEdu.entity.Log;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;

import java.util.List;

public interface LogService {
    Result<IPage<Log>> getLogByUserId(Integer userId, Integer pageNumber, Integer pageSize);
    Result<List<Log>> getLogByUserId(Integer userId,Integer count);
}