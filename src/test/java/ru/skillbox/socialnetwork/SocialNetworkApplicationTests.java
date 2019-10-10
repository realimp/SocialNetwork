package ru.skillbox.socialnetwork;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.services.EMailService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SocialNetworkApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private EMailService eMailService;

	@Test
	public void sendEmail() throws UnsupportedEncodingException, MessagingException {
		eMailService.sendEmail("JavaPro2.SkillBox@mail.ru","Тестовое письмо","Письмо успешно отправлено","Отправка письма выполнена успешна");
	}
}
