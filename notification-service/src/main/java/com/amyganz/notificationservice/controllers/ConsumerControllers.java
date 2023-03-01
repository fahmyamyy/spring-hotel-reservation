package com.amyganz.notificationservice.controllers;//package com.amyganz.notificationservice.controllers;
//
//import com.amyganz.notificationservice.dtos.EmailRequest;
//import com.amyganz.notificationservice.services.EmailService;
//import lombok.AllArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.web.bind.annotation.*;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//@RestController
//@RequestMapping("consumer")
//@AllArgsConstructor
//public class ConsumerControllers {
////    private EmailService emailService;
//
//    private JavaMailSender mailSender;
//    @PostMapping
//    public void sendOtp(@RequestBody EmailRequest emailRequest) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo(emailRequest.getTo());
//        helper.setReplyTo("fahmy.malikk@gmail.com");
//        helper.setSubject("Hotel California: OTP");
//        helper.setText("Your One-time Password (OTP) is:\n" +
//                "<h2>" + emailRequest.getOtp() + "</h2>\n" +
//                "\n" +
//                "If you are having any issues with your account, please don't hesitate to contact us by replying to this email.",true);
//        mailSender.send(message);
//    }
//
////    @PostMapping("cancel")
////    public void cancelEmail(EmailRequest emailRequest) throws MessagingException {
////        MimeMessage message = mailSender.createMimeMessage();
////        MimeMessageHelper helper = new MimeMessageHelper(message);
////        helper.setTo(emailRequest.getTo());
////        helper.setReplyTo("fahmy.malikk@gmail.com");
////        helper.setSubject("Hotel California: Cancel Reservation");
////        helper.setText("Your Reservation canceled :\n" +
////                "<h2>" + emailRequest.getOtp() + "</h2>\n" +
////                "\n" +
////                "If you are having any issues with your account, please don't hesitate to contact us by replying to this email.",true);
////        mailSender.send(message);
////    }
//}
