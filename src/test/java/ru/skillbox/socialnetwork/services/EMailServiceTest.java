package ru.skillbox.socialnetwork.services;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public class EMailServiceTest {
//============================================================
// Тестовая почта
// Адрес: JavaPro2.SkillBox@mail.ru
// Пароль: JP2_SkillBox
//============================================================
    @Autowired
    EMailService eMailService = new EMailService();

    @Test
    public void sendEMailTest() throws UnsupportedEncodingException, MessagingException {

        org.junit.Assert.assertTrue(eMailService.sendEmail("JavaPro2.SkillBox@mail.ru","Тестовое письмо","Письмо успешно отправлено","Отправка письма выполнена успешна"));
        org.junit.Assert.assertFalse(eMailService.sendEmail("notMail","Тестовое письмо","Письмо успешно отправлено","Отправка письма выполнена успешна"));
    }

}
