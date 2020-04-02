package com.sbs.sbsgroup7.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class MailService {

    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";
    private final JavaMailSender javaMailSender;

    @Value("${mail.username}")
    String fromMail;

    public MailService(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(fromMail);
            message.setSubject(subject);
            message.setText(content, isHtml);
            getJavaMailSender().send(mimeMessage);

        } catch (MailException | MessagingException e) {
            System.out.println("Email could not be sent to user '{}'"+ to+ e);
        }
    }

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("cse545sbsgroup7@gmail.com");
        mailSender.setPassword("sbsgroup7");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }


    @Async
    public void sendOTPMail(String email, String otp) {
        sendEmail(email, "OTP for the transaction:", "<html><body>" + otp + "</body></html>",false,true);
    }
}