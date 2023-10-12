package com.github.XiaoFeng2233.CheeseEdu;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@SpringBootApplication
@Slf4j
@EnableCaching
public class CheeseEduApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(CheeseEduApplication.class, args);
		log.info("程序开始运行~");
	}
}
