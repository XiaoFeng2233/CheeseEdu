package com.github.XiaoFeng2233.CheeseEdu.util;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.jarod.qqwry.IPZone;
import com.github.jarod.qqwry.QQWry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/11/1 21:26
 */
@Slf4j
public class MyUtils {
    public static Boolean checkMobileNumber(String number){
        String hex = "^[1][3,4,5,7,8][0-9]{9}$";
        if (StrUtil.hasBlank(number) || number.length() != 11 ){
            return false;
        }else{
            Pattern p = Pattern.compile(hex);
            Matcher matcher = p.matcher(number);
            return matcher.matches();
        }
    }

    public static Boolean checkUsername(String username){
        String hex = "^[A-Za-z]{1}[A-Za-z0-9]*$";
        if (StrUtil.hasBlank(username) || username.length()<4 || username.length()>20){
            return false;
        }else{
            Pattern p = Pattern.compile(hex);
            Matcher matcher = p.matcher(username);
            return matcher.matches();
        }
    }

    public static String getIp(HttpServletRequest request){
        //适用cloudflare或百度云加速cdn
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        //适用创宇云安全cdn
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_REAL_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getIpArea(String ip) {
        ClassPathResource resource = new ClassPathResource("qqwry.dat");
        try {
            byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
            QQWry qqWry = new QQWry(data);
            IPZone ipZone = qqWry.findIP(ip);
            return ipZone.getMainInfo();
        }catch (Exception e){
            log.error("QQwry Ip数据库读取失败");
            e.printStackTrace();
        }
        return "未知地区";
    }

    public String uploadImage(MultipartFile file) throws Exception{
        String date = DateUtil.today();
        String realPath = System.getProperty("user.dir")  + File.separator +  "upload" + File.separator + "user-image" + File.separator + date;
        String fileName = file.getResource().getFilename();
        String suffix = fileName.split("\\.")[fileName.split("\\.").length - 1];
        fileName = RandomUtil.randomString(30) + "." + suffix;
        String contentType = file.getContentType();
        FileUtil.mkdir(realPath);
        if (contentType.equalsIgnoreCase("image/gif") || contentType.equalsIgnoreCase("image/png") || contentType.equalsIgnoreCase("image/jpeg")) {
            System.out.println(System.getProperty("user.dir")  + File.separator +  "upload" + File.separator + "user-image" + File.separator + date + File.separator + fileName);
            File s = new File(System.getProperty("user.dir")+ File.separator + "upload" + File.separator + "user-image" + File.separator + date + File.separator + fileName);
            file.transferTo(s);
            return "/upload/user-image/" + date + "/" + fileName;
        } else {
            return null;
        }
    }

}
