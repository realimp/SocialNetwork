package ru.skillbox.socialnetwork.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class EMailService {
    private String mailServerHostName = "smtp.mail.ru";
    private int mailServerPort = 587;
    private String mailServerUsername = "JavaPro2.SkillBox@mail.ru";
    private String mailServerPassword = "JP2_SkillBox";
    private String senderName = mailServerUsername;
    private String senderEmail = mailServerUsername;

    public EMailService () {
    }

    private Properties emailProperties = new Properties();
    {
        emailProperties.put("mail.transport.protocol", "smtp");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.starttls.required", "true");
    }

    public boolean sendEmail(String recipientEmail, String subject,
                          String messageTextBody, String messageHtmlBody) throws MessagingException, UnsupportedEncodingException {
        Session mailSession = Session.getInstance(emailProperties);
        Transport transport = null;

        boolean result = true;

        try {
            //region Создание multipart контента
            Multipart content = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(messageTextBody, "text/plain");
            content.addBodyPart(textPart);
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(messageHtmlBody, "text/html;charset=\"UTF-8\"");
            //endregion

            //region Инициализация MimeMessage
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject, StandardCharsets.UTF_8.name());
            content.addBodyPart(htmlPart);
            message.setContent(content);
            message.setFrom(new InternetAddress(senderName, senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            //endregion
            transport = mailSession.getTransport();
            transport.connect(mailServerHostName, mailServerPort, mailServerUsername, mailServerPassword);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        }
        catch (MessagingException e)
        {
            System.out.println("Ошибка");
            result = false;
        }
            finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    System.out.println("Ошибка при закрытии transport " + e.toString());
                    result = false;
                }
            }
        }
        return result;
    }
}
