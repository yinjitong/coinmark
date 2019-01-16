package com.flc.coinmarket.app;
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
@ComponentScan(value = {"com.flc.coinmarket.dao.mongo.*","com.flc.coinmarket.app.*","com.flc.coinmarket.dao.business"})
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
