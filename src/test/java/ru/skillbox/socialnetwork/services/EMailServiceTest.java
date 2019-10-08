package ru.skillbox.socialnetwork.services;


import org.junit.Test;
import org.springframework.util.Assert;
import ru.skillbox.socialnetwork.services.EMailService;

public class EMailServiceTest {
    @Test
    public void sendEMailTest()
    {
//============================================================
// Тестовая почта
// Адрес: JavaPro2.SkillBox@mail.ru
// Пароль: JP2_SkillBox
//============================================================

        EMailService eMailService = new EMailService();
        org.junit.Assert.assertFalse(eMailService.sendEMail("notMail","notMail","",""));
        org.junit.Assert.assertTrue(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","JavaPro2.SkillBox@mail.ru","Subject Test","Body Test"));
    }
}
