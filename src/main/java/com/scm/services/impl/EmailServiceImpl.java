package com.scm.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.scm.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender eMailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender eMailSender) {
        this.eMailSender = eMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("divyankgupta001@gmail.com");
        
        eMailSender.send(message);
        logger.info("Email sent successfully to {}", to);
    }

    @Override
    public void sendEmailWithHTML(String to, String subject, String htmlContent) {
        MimeMessage message = eMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("divyankgupta001@gmail.com");

            eMailSender.send(message);
            logger.info("HTML email sent successfully to {}", to);
        } catch (MessagingException e) {
            logger.error("Error sending HTML email to {}", to, e);
        }
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, File file) {
        MimeMessage message = eMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom("divyankgupta001@gmail.com");

            FileSystemResource fileResource = new FileSystemResource(file);
            helper.addAttachment(fileResource.getFilename(), file);

            eMailSender.send(message);
            logger.info("Email with attachment sent successfully to {}", to);
        } catch (MessagingException e) {
            logger.error("Error sending email with attachment to {}", to, e);
        }
    }

    @Override
    public void sendEmail(String[] to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("divyankgupta001@gmail.com");

        eMailSender.send(message);
        logger.info("Email sent successfully to multiple recipients");
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, InputStream is) {
        MimeMessage message = eMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom("divyankgupta001@gmail.com");

            File file = new File("src/main/resources/email/test.png");
            Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            FileSystemResource fileResource = new FileSystemResource(file);
            helper.addAttachment(fileResource.getFilename(), file);

            eMailSender.send(message);
            logger.info("Email with attachment sent successfully to {}", to);
        } catch (MessagingException | IOException e) {
            logger.error("Error sending email with attachment to {}", to, e);
        }
    }
}
