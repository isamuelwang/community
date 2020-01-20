package com.owwang.community.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.owwang.community.util.IdWorker;

/**
 * @Classname SpitApplication
 * @Description TODO
 * @Date 2020-01-09
 * @Created by WANG
 */
@SpringBootApplication
public class SpitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpitApplication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
