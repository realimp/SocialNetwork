package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EMailService {

    @Value("${email.host}")
    private String host;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    public boolean sendEMail(String from, String to, String subject, String body) {
        boolean result = true;
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", "2525");
        prop.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(prop
                , new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        }
        );
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            result = false;

        }
        return result;
    }
}
