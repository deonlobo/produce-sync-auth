package com.boom.producesyncauth.service.impl;

import com.boom.producesyncauth.data.Role;
import com.boom.producesyncauth.data.UserProfile;
import com.boom.producesyncauth.service.Sender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
  public void send(UserProfile userProfile, String aToken) {
    String url = userProfile.getRole().equals(Role.SELLER) ? "http://localhost:5173/seller/home/"+aToken : "http://localhost:5173/buyer/home/"+aToken;
    String type = userProfile.getRole().equals(Role.SELLER) ? "Seller" : "Buyer";
    System.out.println(url);
    System.out.println("From "+ from+ " to "+ userProfile.getUsername());
    try {
      MimeMessage mimeMessage = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      helper.setFrom(from);
      helper.setTo(userProfile.getUsername());
      helper.setSubject("Signin link for ProduceSync "+type+" Account");

      // Construct HTML content for the email body
      String htmlContent = "<html><body>" +
              "<p style=\"font-family: Arial, sans-serif; font-size: 16px;\">Hello " + userProfile.getFirstName() + "</p>" +
              "<p style=\"font-family: Arial, sans-serif; font-size: 16px;\">A login attempt was made to access your account. For this purposes, we have generated a login token for you.</p>" +
              "<p style=\"font-family: Arial, sans-serif; font-size: 16px;\">Use the following link to securely access your account: <a href=\"" + url + "\" style=\"color: #007bff; text-decoration: none;\">Login</a>.</p>" +
              "<p style=\"font-family: Arial, sans-serif; font-size: 16px;\">Alternatively, you can copy and paste the following link into your browser:</p>" +
              "<p style=\"font-family: Arial, sans-serif; font-size: 16px;\"><a href=\"" + url + "\" style=\"color: #007bff; text-decoration: none;\">" + url + "</a></p>" +
              "<p style=\"font-family: Arial, sans-serif; font-size: 16px;\">Please do not share this login link with anyone.</p>" +
              "<p style=\"font-family: Arial, sans-serif; font-size: 16px;\">Thank you!</p>" +
              "</body></html>";


      helper.setText(htmlContent, true); // Set the text content as HTML

      emailSender.send(mimeMessage);
      System.out.println("Email Sent");
    } catch (MessagingException e) {
      // Handle exception
      e.printStackTrace();
    }
  }

}
