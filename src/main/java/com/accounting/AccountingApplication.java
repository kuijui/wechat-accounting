package com.accounting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.accounting.mapper")
public class AccountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingApplication.class, args);
        System.out.println("\n==================================");
        System.out.println("微信记账小程序后端启动成功！");
        System.out.println("API文档地址: http://localhost:8080/api/doc.html");
        System.out.println("==================================");
    }
}