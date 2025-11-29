package com.mailservice.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
//Comment the code bec. it work but not support mail html style
//@Service
//public class EmailService {
//    @Autowired
//    private JavaMailSender mailSender;
//    /* The JavaMailSender object is automatically provided by Spring.
//       It is responsible for handling the actual process of sending emails. */
//
//    public void sendEmail(String to, String subject, String text) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        /* A SimpleMailMessage object is created.
//           This object holds the details of the email such as receiver,
//           subject line, and the body content. */
//
//        message.setTo(to);
//        /* The recipient email address is set here.
//           This value comes from the method parameter. */
//
//        message.setSubject(subject);
//        /* The subject of the email is assigned based on the parameter
//           provided when the method is called. */
//
//        message.setText(text);
//        /* The body text of the email is assigned here.
//           This contains the final message that will be delivered to the customer. */
//
//        mailSender.send(message);
//        /* The mailSender object sends the email to the receiver.
//           Spring handles the SMTP communication in the background. */
//
//        System.out.println("Email sent to: " + to);
//        /* This message is displayed in the console so developers can confirm
//           that the email was successfully sent. */
//    }
//}
/**
 * EmailService handles sending emails to users.
 * This version supports sending HTML emails so that styled content
 * like tables, paragraphs, and bold text can be rendered in the email client.
 *
 * Author: Ram Choudhary
 * Date: 28-Nov-2025
 */

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    /* JavaMailSender is automatically provided by Spring Boot.
       It handles the underlying SMTP communication to send emails. */

    /**
     * Sends an HTML email to the specified recipient.
     *
     * @param to       Recipient email address
     * @param subject  Subject of the email
     * @param htmlBody HTML content of the email, can include <p>, <table>, <b> etc.
     *
     * This method uses MimeMessage to allow HTML rendering in the email client.
     * The second parameter in helper.setText(htmlBody, true) is set to true
     * to indicate that the content is HTML.
     */
    public void sendEmail(String to, String subject, String htmlBody) {
        try {
            // Create a MIME message object
            MimeMessage message = mailSender.createMimeMessage();

            // MimeMessageHelper helps set the details of the message
            // 'true' parameter indicates multipart message (supports attachments if needed)
            // 'UTF-8' ensures proper encoding for international characters
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Set recipient email
            helper.setTo(to);

            // Set email subject
            helper.setSubject(subject);

            // Set email content as HTML
            helper.setText(htmlBody, true); // true = HTML content

            // Send the email using JavaMailSender
            mailSender.send(message);

            System.out.println("HTML Email sent to: " + to);

        } catch (Exception e) {
            // Print stack trace in case of failure
            e.printStackTrace();
        }
    }
}

