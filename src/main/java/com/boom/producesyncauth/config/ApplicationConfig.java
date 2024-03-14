package com.boom.producesyncauth.config;

import com.boom.producesyncauth.service.Sender;
import com.boom.producesyncauth.service.impl.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class ApplicationConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("producesyncauth@gmail.com");
        mailSender.setPassword("qeebdpkyzzfbbfdu");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", true); // Enable STARTTLS
        props.put("mail.smtp.starttls.required", "true"); // Require STARTTLS
        props.put("mail.debug", "true");

        return mailSender;
    }*/


}
