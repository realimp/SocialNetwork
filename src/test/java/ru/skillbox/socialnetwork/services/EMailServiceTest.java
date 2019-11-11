//Автор:
//Имя: Дмитрий Хрипков
//Псевдоним: X64
//Почта: HDV_1990@mail.ru
package ru.skillbox.socialnetwork.services;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

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
