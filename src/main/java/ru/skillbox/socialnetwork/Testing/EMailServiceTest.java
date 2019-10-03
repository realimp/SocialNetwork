package ru.skillbox.socialnetwork.Testing;

import org.junit.Assert;
import org.junit.Test;
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
        //public boolean sendEMail(String from, String to, String subject, String body)

        //Отсутствует отправитель
        Assert.assertFalse(eMailService.sendEMail("","","",""));
        Assert.assertFalse(eMailService.sendEMail("","","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("","","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("","","Subject Test","Body Test"));

        Assert.assertFalse(eMailService.sendEMail("","notMail","",""));
        Assert.assertFalse(eMailService.sendEMail("","notMail","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("","notMail","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("","notMail","Subject Test","Body Test"));

        Assert.assertFalse(eMailService.sendEMail("","JavaPro2.SkillBox@mail.ru","",""));
        Assert.assertFalse(eMailService.sendEMail("","JavaPro2.SkillBox@mail.ru","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("","JavaPro2.SkillBox@mail.ru","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("","JavaPro2.SkillBox@mail.ru","Subject Test","Body Test"));

        //Адрес отправителя не соответствует формату
        Assert.assertFalse(eMailService.sendEMail("notMail","","",""));
        Assert.assertFalse(eMailService.sendEMail("notMail","","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("notMail","","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("notMail","","Subject Test","Body Test"));

        Assert.assertFalse(eMailService.sendEMail("notMail","notMail","",""));
        Assert.assertFalse(eMailService.sendEMail("notMail","notMail","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("notMail","notMail","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("notMail","notMail","Subject Test","Body Test"));

        Assert.assertFalse(eMailService.sendEMail("notMail","JavaPro2.SkillBox@mail.ru","",""));
        Assert.assertFalse(eMailService.sendEMail("notMail","JavaPro2.SkillBox@mail.ru","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("notMail","JavaPro2.SkillBox@mail.ru","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("notMail","JavaPro2.SkillBox@mail.ru","Subject Test","Body Test"));

        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","","",""));
        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","","Subject Test","Body Test"));

        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","notMail","",""));
        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","notMail","","Body Test"));
        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","notMail","Subject Test",""));
        Assert.assertFalse(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","notMail","Subject Test","Body Test"));

        //Отправитель и получатель соответствует
        Assert.assertTrue(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","JavaPro2.SkillBox@mail.ru","Subject Test","Body Test"));
        Assert.assertTrue(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","JavaPro2.SkillBox@mail.ru","",""));
        Assert.assertTrue(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","JavaPro2.SkillBox@mail.ru","","Body Test"));
        Assert.assertTrue(eMailService.sendEMail("JavaPro2.SkillBox@mail.ru","JavaPro2.SkillBox@mail.ru","Subject Test",""));

    }
}
