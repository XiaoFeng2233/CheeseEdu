package com.github.XiaoFeng2233.CheeseEdu.controller.admin;

import com.github.XiaoFeng2233.CheeseEdu.dao.SmsConfigMapper;
import com.github.XiaoFeng2233.CheeseEdu.entity.*;
import com.github.XiaoFeng2233.CheeseEdu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/do/admin/config")
public class ConfigController {

    @Autowired
    private BannerConfigService bannerConfigService;
    @Autowired
    private GlobalConfigService globalConfigService;
    @Autowired
    private StorageConfigService storageConfigService;
    @Autowired
    private PaymentConfigService paymentConfigService;
    @Autowired
    private SmsConfigService smsConfigService;

    @GetMapping("/getallbanner.do")
    public Result<List<BannerConfig>> getBanner() {
        return bannerConfigService.getAllBannerList();
    }

    @PostMapping("/getbannerbyid.do")
    public Result<BannerConfig> getBannerById(@RequestParam Integer id) {
        return bannerConfigService.getBannerById(id);
    }

    @PostMapping("/updatebannerbyid.do")
    public Result<String> updateBannerById(@RequestBody BannerConfig bannerConfig) {
        return bannerConfigService.updateBannerById(bannerConfig);
    }

    @PostMapping("/addbanner.do")
    public Result<String> addBanner(@RequestBody BannerConfig bannerConfig) {
        return bannerConfigService.insertBanner(bannerConfig);
    }

    @GetMapping("/deletebanner.do/{id}")
    public Result<String> deleteBanner(@PathVariable Integer id) {
        return bannerConfigService.deleteBanner(id);
    }

    @GetMapping("/getglobalconfig.do")
    public Result<GlobalConfig> getGlobalConfig() {
        return globalConfigService.getConfig();
    }

    @PostMapping("/updateglobalconfig.do")
    public Result<String> updateGlobalConfig(@RequestBody GlobalConfig globalConfig) {
        return globalConfigService.updateGlobalConfig(globalConfig);
    }

    @GetMapping("/getstorageconfig.do")
    public Result<StorageConfig> getStorageConfig() {
        return storageConfigService.getStorageConfig();
    }

    @PostMapping("/updatestorageconfig.do")
    public Result<String> updateStorageConfig(@RequestBody StorageConfig storageConfig) {
        return storageConfigService.updateStorageConfig(storageConfig);
    }

    @PostMapping("/updatepaymentconfig.do")
    public Result<String> updatePaymentConfig(@RequestBody PaymentConfig paymentConfig) {
        return paymentConfigService.updatePaymentConfig(paymentConfig);
    }

    @GetMapping("/getpaymentconfig.do")
    public Result<PaymentConfig> getPaymentConfig(){
        return paymentConfigService.getPaymentConfig();
    }

    @PostMapping("/updatesmsconfig.do")
    public Result<String> updateSmsConfig(@RequestBody SmsConfig smsConfig){
        smsConfigService.updateSmsConfig(smsConfig);
        return new Result<String>().success("修改成功");
    }

    @GetMapping("/getsmsconfig.do")
    public Result<SmsConfig> getSmsConfig(){
        SmsConfig smsConfig = smsConfigService.getSmsConfig();
        return new Result<SmsConfig>().success(smsConfig);
    }
}
