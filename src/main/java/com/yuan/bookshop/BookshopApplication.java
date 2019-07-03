package com.yuan.bookshop;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.context.annotation.FilterType.CUSTOM;

@SpringBootApplication
@MapperScan("com.yuan.bookshop.dao")
@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})
@ComponentScan(excludeFilters={@ComponentScan.Filter(type=CUSTOM, classes={TypeExcludeFilter.class}), @ComponentScan.Filter(type=CUSTOM, classes={AutoConfigurationExcludeFilter.class})})
public class BookshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

}
