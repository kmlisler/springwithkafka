package com.kamilisler.springwithkafka;

import com.kamilisler.springwithkafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringwithkafkaApplication{
	public static void main(String[] args) {
		SpringApplication.run(SpringwithkafkaApplication.class, args);
	}

}
