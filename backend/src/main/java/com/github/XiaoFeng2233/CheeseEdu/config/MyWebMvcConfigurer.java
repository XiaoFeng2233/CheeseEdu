package com.github.XiaoFeng2233.CheeseEdu.config;

import com.github.XiaoFeng2233.CheeseEdu.interceptor.AdminInterceptor;
import com.github.XiaoFeng2233.CheeseEdu.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/12/13 20:57
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public AdminInterceptor adminInterceptor(){
        return new AdminInterceptor();
    };

    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/do/admin/**");
        registry.addInterceptor(userInterceptor())
                .addPathPatterns("/do/**")
                .excludePathPatterns("/do/login/**")
                .excludePathPatterns("/do/register/**")
                .excludePathPatterns("/do/common/**");
    }
}
