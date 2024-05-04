package tn.elaainternational.reservation.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.elaainternational.reservation.Models.UserMail;
import tn.elaainternational.reservation.repository.IUserEmailRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class EmailServiceImpl implements IUserEmailRepository {

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void SendReservationByMail(UserMail mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("saebizmatch@gmail.com");
            helper.setTo(mail.getTo());
            helper.setSubject("Reservation for Service");

            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 50px;'>" +
                    "<div style='background-color: #ffffff; padding: 40px; border-radius: 8px; max-width: 600px; margin: auto;'>" +
                    "<h1 style='color: #333333; font-size: 24px; margin-bottom: 20px;'>Welcome to Elaa International!</h1>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px;'>Thanks for signing up. Please use the following code to complete your registration:</p>" +
                    "<p style='background-color: #48cfae; display: inline-block; padding: 10px 20px; color: #ffffff; border-radius: 4px; font-size: 18px; font-weight: bold;'>" + mail.getCode() + "</p>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-top: 20px;'>If you didn’t make this request, you can ignore this email.</p>" +
                    "</div></div>";

            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendReservationConfirmationMail(String Useremail, String serviceName, LocalDateTime reservationDateTime) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("saebizmatch@gmail.com");
            helper.setTo(Useremail);
            helper.setSubject("Reservation Confirmation");

            // Format reservation date time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedReservationDateTime = reservationDateTime.format(formatter);

            String htmlMsg = "<div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 50px;'>" +
                    "<div style='background-color: #ffffff; padding: 40px; border-radius: 8px; max-width: 600px; margin: auto;'>" +
                    "<h1 style='color: #333333; font-size: 24px; margin-bottom: 20px;'>Reservation Confirmation</h1>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px;'>Your reservation for the service <strong>" + serviceName + "</strong> has been confirmed.</p>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px;'>Reservation Date & Time: <strong>" + formattedReservationDateTime + "</strong></p>" +
                    "<p style='color: #666666; font-size: 16px; line-height: 1.5; margin-top: 20px;'>If you have any questions, please feel free to contact us.</p>" +
                    "</div></div>";

            message.setContent(htmlMsg, "text/html");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
