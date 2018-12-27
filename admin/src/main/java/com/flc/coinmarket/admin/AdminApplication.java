package com.flc.coinmarket.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableTransactionManagement
@MapperScan(value = "com.flc.coinmarket.dao.mysql.mapper.*")
@ComponentScan(basePackages = {"com.flc.coinmarket.admin", "com.flc.coinmarket.dao.mongo"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);

    }
}
