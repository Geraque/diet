package com.example.demo.service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

  private String host = "smtp.mail.ru";
  private String port = "465";
  private String username = "alekspavlov04@mail.ru";
  private String password = "nARdMzbWBLAyQUmZpvYg";


  public void sendEmail(String toEmail, String subject, String body) {
    Properties properties = System.getProperties();

    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    session.setDebug(true);

    try {
      MimeMessage message = new MimeMessage(session);

      message.setFrom(new InternetAddress(username));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
      message.setSubject(subject);
      message.setText(body);

      Transport.send(message);
      System.out.println("Письмо успешно отправлено");
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}
