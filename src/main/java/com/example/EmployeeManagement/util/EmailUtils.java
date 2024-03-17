package com.example.EmployeeManagement.util;

import com.example.EmployeeManagement.model.User;
import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender javaMailSender;

    public EmailUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendWelcomeEmail(User user, String generatedPassword) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to the Employee Management Admin Panel");

            String emailContent = "Dear " + user.getName() + ",\n\n"
                    + "We are delighted to welcome you to the Employee Management Admin Panel! Your account has been successfully created, granting you access to powerful tools for efficient administration.\n\n"
                    + "Below are your login details:\n"
                    + "Email Address: " + user.getEmail() + "\n"
                    + "Password: " + generatedPassword + "\n\n"
                    + "Should you have any inquiries or require assistance, our dedicated support team stands ready to assist you.\n\n"
                    + "Warm regards,\n"
                    + "The Employee Management Team";

            helper.setText(emailContent);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

}
