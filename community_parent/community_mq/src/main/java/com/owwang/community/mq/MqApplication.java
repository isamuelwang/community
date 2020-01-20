package com.owwang.community.mq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.owwang.community.util.IdWorker;

@SpringBootApplication
public class MqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
