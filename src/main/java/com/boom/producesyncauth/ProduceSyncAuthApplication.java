package com.boom.producesyncauth;

import com.boom.producesyncauth.service.Sender;
import com.boom.producesyncauth.service.impl.EmailSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class ProduceSyncAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProduceSyncAuthApplication.class, args);
	}

}
