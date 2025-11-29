package com.mailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class MailServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(MailServiceApplication.class, args);
        System.out.println("I am On Kafka");
	}

}
