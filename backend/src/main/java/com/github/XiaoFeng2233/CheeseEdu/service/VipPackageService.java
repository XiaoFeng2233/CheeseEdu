package com.github.XiaoFeng2233.CheeseEdu.service;

import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.entity.VipPackage;

import java.util.List;

public interface VipPackageService {

    Result<String> addVipPackage(VipPackage vipPackage);

    Result<List<VipPackage>> getAllVipPackage();

    Result<String> deleteVipPackage(int id);

    Result<VipPackage> getVipPackageById(int id);

    Result<String> updateVipPackageById(VipPackage vipPackage);
}
