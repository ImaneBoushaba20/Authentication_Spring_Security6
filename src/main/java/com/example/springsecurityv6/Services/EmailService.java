package com.example.springsecurityv6.Services;

import com.example.springsecurityv6.Models.RetrieveAccount;
import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Repositories.RetrieveAccountRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final RetrieveAccountRepository repository;




    public String sendCodeReNewPassword(User u) {
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1));

        String subject = "New Password";
        String senderName = "Salah-eddine Community Springxboot  ";
        String mailContent = "<p> Hi, "+ u.getEmail()+ ", </p>"+

                "you might be forgot your password here is the code to change a new password.</p>"+
                "<h2>"+code+"</h2>"+
                "<p> Thank you !!";
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("ramdanisalah67@gmail.com", senderName);
            messageHelper.setTo(u.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
        }
        catch (Exception e){System.out.println(e.getMessage());}


        repository.save(new RetrieveAccount(null,code,u));

        return "ok";
    }
    public String sendCredentialsToUser(User u) {
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1));

        String subject = "";
        String senderName = "CompanyEHEI";
        String mailContent = "<p> Hi, "+ u.getEmail()+ ", </p>"+

                "welcome to our Website . here is infos about login :</p>"+
                "<h2>Email => "+u.getEmail()+" </h2>"+
                "<h2>Password</h2>";
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("ramdanisalah67@gmail.com", senderName);
            messageHelper.setTo(u.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
        }
        catch (Exception e){System.out.println(e.getMessage());}


        repository.save(new RetrieveAccount(null,code,u));

        return "ok";
    }
}
