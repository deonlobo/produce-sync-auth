package com.boom.producesyncauth.service.impl;

import com.boom.producesyncauth.data.Role;
import com.boom.producesyncauth.data.UserProfile;
import com.boom.producesyncauth.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Arik Cohen
 * @since Jan 30, 2018
 */
@Service
public class EmailSender implements Sender {
  
  @Value("${passwordless.email.from}")
  private String from;

  @Autowired
  private JavaMailSender emailSender;

  public EmailSender(JavaMailSender aJavaMailSender) {
  }

  @Override
  public void send (UserProfile userProfile, String aToken) {
    String url="";
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom(from);
    mailMessage.setTo(userProfile.getUsername());
    if(userProfile.getRole().equals(Role.SELLER)){
      url = "http://localhost:5173/seller/home/";
    }else{
      url = "http://localhost:5173/buyer/home/";
    }
    mailMessage.setSubject("Your signin link");
    mailMessage.setText(String.format("Hello!\nAccess your account here: %s%s",url,aToken));
    
    emailSender.send(mailMessage);
  }

}
