package com.owwang.community.recuit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.owwang.community.util.IdWorker;
@SpringBootApplication
public class RecuitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecuitApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
