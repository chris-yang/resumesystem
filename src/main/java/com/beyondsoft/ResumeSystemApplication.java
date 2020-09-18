package com.beyondsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.beyondsoft.mapper")
@EnableScheduling

public class ResumeSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResumeSystemApplication.class, args);
    }
}
