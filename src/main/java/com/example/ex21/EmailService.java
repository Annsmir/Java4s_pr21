package com.example.ex21;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Transport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Value("${mail.smtp.host}")
    private String host; 

    @Value("${mail.from}")
    private String from;

    @Value("${mail.rcpt}")
    private String rcpt;
    
    @Async
    public void sendEmail(String subj, String msg) {
        long t = Thread.currentThread().getId();        
        log.info("Email service (javax.mail) on thread {} sendEmail( {} )", t, msg);

        Properties p = System.getProperties();
        p.setProperty("mail.smtp.host", host);
        Session s = Session.getDefaultInstance(p);

        try {
            MimeMessage m = new MimeMessage(s);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(rcpt));
            m.setSubject(subj, "UTF-8");
            m.setContent(msg, "text/plain; charset=UTF-8"); // can also send HTML
   
            Transport.send(m);

            log.info("Sent message successfully....");
         } catch (MessagingException e) {
            e.printStackTrace();
         }


    }
}
