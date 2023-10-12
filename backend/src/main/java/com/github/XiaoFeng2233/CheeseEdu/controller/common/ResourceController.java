package com.github.XiaoFeng2233.CheeseEdu.controller.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.qcloud.cos.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ResourceController {

    @RequestMapping("/testupload")
    @ResponseBody
    public String upload(@RequestParam("files") MultipartFile files[]) throws IOException {
        JSONArray jsonArray = new JSONArray();

        if (files == null || files.length <= 0){
            return "huq";
        }
        for (MultipartFile file : files) {
            String date = DateUtil.today();
            String path = ResourceUtils.getFile("classpath:").getPath() +  File.separator + "static" + File.separator +  "upload" + File.separator + "image" + File.separator + date;
            String fileName = file.getResource().getFilename();
            String suffix = fileName.split("\\.")[fileName.split("\\.").length - 1];
            String realPath = path;
            fileName = RandomUtil.randomString(30) + "." + suffix;
            String contentType = file.getContentType();
            FileUtil.mkdir(realPath);
            if (contentType.equalsIgnoreCase("image/gif") || contentType.equalsIgnoreCase("image/png") || contentType.equalsIgnoreCase("image/jpeg")) {
                File s = new File(realPath,fileName);
                file.transferTo(s.getAbsoluteFile());
                jsonArray.add("/upload/image/" + date + "/" + fileName);
            } else {
                return null;
            }
        }
        return null;
    }

}
