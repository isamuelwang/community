package com.owwang.community.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.owwang.community.util.IdWorker;

/**
 * @Classname SearchApllication
 * @Description TODO
 * @Date 2020-01-10
 * @Created by WANG
 */
@SpringBootApplication
public class SearchApllication {

    public static void main(String[] args){
        SpringApplication.run(SearchApllication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
