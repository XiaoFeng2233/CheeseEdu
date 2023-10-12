package com.github.XiaoFeng2233.CheeseEdu.service.impl;

import com.github.XiaoFeng2233.CheeseEdu.dao.VipPackageMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.VipPackage;
import com.github.XiaoFeng2233.CheeseEdu.service.VipPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class VipPackageServiceImpl implements VipPackageService {

    @Autowired
    private VipPackageMapper mapper;


    @Override
    public Result<String> addVipPackage(VipPackage vipPackage) {
        if (vipPackage.getPrice() == null || vipPackage.getDuration() == 0 || vipPackage.getName() == null) {
            return new
                    Result<>().wrongParams();
        }

        if (vipPackage.getPrice().compareTo(new BigDecimal(0)) != 1) {
            return new
                    Result<>().wrongParams();
        }

        if (vipPackage.getDuration() < 1) {
            return new
                    Result<>().wrongParams();
        }

        vipPackage.setPrice(vipPackage.getPrice().setScale(2, RoundingMode.DOWN));
        int i = mapper.insert(vipPackage);
        if (i > 0) {
            return new Result<String>().success("套餐添加成功");
        } else {
            return new Result<String>().error("套餐添加失败,发生未知错误");
        }
    }

    @Override
    public Result<List<VipPackage>> getAllVipPackage() {
        List<VipPackage> list = mapper.selectList(null);
        return new Result<List<VipPackage>>().success(list);
    }

    @Override
    public Result<String> deleteVipPackage(int id) {
        int i = mapper.deleteById(id);
        if (i>0){
            return new Result<String>().success("删除成功");
        }
        return new Result<String>().error("删除失败,发生未知错误");
    }

    @Override
    public Result<VipPackage> getVipPackageById(int id) {
        VipPackage vipPackage = mapper.selectById(id);
        return new Result<VipPackage>().success(vipPackage);
    }

    @Override
    public Result<String> updateVipPackageById(VipPackage vipPackage) {
        int i = mapper.updateById(vipPackage);
        if (i>0){
            return new Result<String>().success("修改成功");
        }
        return new Result<String>().error("修改失败,发生未知错误");
    }
}
