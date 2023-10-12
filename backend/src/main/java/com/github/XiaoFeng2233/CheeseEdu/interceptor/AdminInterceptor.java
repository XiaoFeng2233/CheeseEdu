package com.github.XiaoFeng2233.CheeseEdu.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.Claim;
import com.github.XiaoFeng2233.CheeseEdu.entity.Result;
import com.github.XiaoFeng2233.CheeseEdu.util.JWTUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/11/13 20:43
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        response.setContentType("text/json;charset=UTF-8");
        Result<String> result = null;
        if (StrUtil.hasBlank(token)){
            result = new Result<>(999,"您还没有登录,","NeedLogin");
            response.getWriter().write(new Gson().toJson(result));
        }else{
            Boolean b = jwtUtil.verify(token);
            if (b){
                Claim isAdmin = jwtUtil.getClaimByName(token, "isAdmin");
                Integer s = isAdmin.asInt();
                if (s==1){
                    return true;
                }else{
                    result = new Result<>(999,"您还没有登录,","NeedLogin");
                    response.getWriter().write(new Gson().toJson(result));
                }
            }else{
                result = new Result<>(999,"登录超时或无效,","LoginTimeOut");
                response.getWriter().write(new Gson().toJson(result));
            }
        }
        return false;
    }
}
