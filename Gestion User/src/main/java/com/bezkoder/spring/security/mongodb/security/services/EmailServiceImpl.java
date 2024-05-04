package com.bezkoder.spring.security.mongodb.security.services;

import com.bezkoder.spring.security.mongodb.models.User;
import com.bezkoder.spring.security.mongodb.models.UserMail;
import com.bezkoder.spring.security.mongodb.repository.IUserEmailRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;




@Service
@Slf4j
public class EmailServiceImpl implements IUserEmailRepository {
/*
    @Value("${app.TWILIO_AUTH_TOKEN}")
    private  String Service_TWILIO_AUTH_TOKEN;
    @Value("${app.WILIO_ACCOUNT_SID}")
    private  String Service_TWILIO_ACCOUNT_SID;
    */


    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private VerificationTokenService verificationTokenService;

    @Override
    public void sendCodeByMail(UserMail mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("saebizmatch@gmail.com");
            helper.setTo(mail.getTo());
            helper.setSubject("Code d'activation");

            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 50px;'>" +
                    "<div style='background-color: #ffffff; padding: 40px; border-radius: 8px; max-width: 600px; margin: auto;'>" +
                    "<h1 style='color: #333333; font-size: 24px; margin-bottom: 20px;'>Bienvenue chez Elaa International !</h1>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px;'>Merci de vous être inscrit. Veuillez utiliser le code suivant pour compléter votre inscription :</p>" +
                    "<p style='background-color: #48cfae; display: inline-block; padding: 10px 20px; color: #ffffff; border-radius: 4px; font-size: 18px; font-weight: bold;'>" + mail.getCode() + "</p>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-top: 20px;'>Si vous n'avez pas effectué cette demande, vous pouvez ignorer cet e-mail.</p>" +
                    "</div></div>";

            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void sendcodereset(UserMail mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("saebizmatch@gmail.com");
            helper.setTo(mail.getTo());
            helper.setSubject("Code de réinitialisation du mot de passe");

            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 50px;'>" +
                    "<div style='background-color: #ffffff; padding: 40px; border-radius: 8px; max-width: 600px; margin: auto;'>" +
                    "<h1 style='color: #333333; font-size: 24px; margin-bottom: 20px;'>Demande de réinitialisation du mot de passe</h1>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px;'>Vous avez demandé à réinitialiser votre mot de passe. Veuillez utiliser le code suivant pour réinitialiser votre mot de passe:</p>" +
                    "<p style='background-color: #48cfae; display: inline-block; padding: 10px 20px; color: #ffffff; border-radius: 4px; font-size: 18px; font-weight: bold;'>" + mail.getCode() + "</p>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-top: 20px;'>Si vous n'avez pas effectué cette demande, veuillez nous contacter immédiatement.</p>" +
                    "</div></div>";

            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }




    public void sendVerificationEmail(User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getEmail());
            user.setVerificationToken(verificationTokenService.generateVerificationToken());
            helper.setSubject("Vérification du compte");

            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 50px;'>" +
                    "<div style='background-color: #ffffff; padding: 40px; border-radius: 8px; max-width: 600px; margin: auto;'>" +
                    "<h1 style='color: #333333; font-size: 24px; margin-bottom: 20px;'>Bonjour " + user.getUsername() + ",</h1>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px;'>Veuillez cliquer sur le lien ci-dessous pour activer votre compte :</p>" +
                    "<a href='http://localhost:9098/api/auth/activate?token=" + user.getVerificationToken() + "' style='background-color: #48cfae; display: inline-block; padding: 10px 20px; color: #ffffff; border-radius: 4px; font-size: 18px; font-weight: bold; text-decoration: none;'>Activer le compte</a>" +
                    "</div></div>";

            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }




    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

/*
    @Async
    public String SendSms(String Phone, String message){
        Twilio.init(Service_TWILIO_ACCOUNT_SID, Service_TWILIO_AUTH_TOKEN);
        Message.creator(new PhoneNumber(Phone),
                new PhoneNumber("+12764449446"), message).create();
        log.info("Sms Send");
        return "Message sent successfully";
    }
    */


}
