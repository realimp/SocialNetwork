package ru.skillbox.socialnetwork;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.EMailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SocialNetworkApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private EMailService eMailService;

    @Test
    public void sendEmail() {
        eMailService.sendEMail("test@gmail.com", "test@gmail.com", "Test", "Test mail");
    }
}
