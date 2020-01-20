package com.owwang.community.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import com.owwang.community.util.IdWorker;

/**
 * @Classname BaseApplication
 * @Description TODO
 * @Date 2020-01-06
 * @Created by WANG
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
