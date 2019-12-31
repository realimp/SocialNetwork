package ru.skillbox.socialnetwork.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.api.requests.Email;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.NotificationSettings;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private Person person;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        person = personRepository.findByEMail("test@email.com");
    }

    @Test
    public void registerTest() {
        Register register = new Register();
        register.setEmail("test2@email.com");
        register.setPasswd1("pass1");
        register.setPasswd2("pass1");
        register.setFirstName("firstName");
        register.setLastName("lastName");
        register.setCode("3675");

        Response<MessageResponse> result = accountService.register(register);
        Person savedPerson = personRepository.findByEMail("test2@email.com");
        Assert.assertEquals("test2@email.com", savedPerson.getEMail());
        Assert.assertEquals("firstName", savedPerson.getFirstName());
        Assert.assertEquals("lastName", savedPerson.getLastName());
        Assert.assertEquals("ok", result.getData().getMessage());
    }

    @Test
    public void registerFailTest() {
        Register registerFail = new Register();
        registerFail.setEmail("test2@email.com");
        registerFail.setPasswd1("pass1");
        registerFail.setPasswd2("pass2");
        registerFail.setFirstName("firstName");
        registerFail.setLastName("lastName");
        registerFail.setCode("code123");

        Response<MessageResponse> resultFail = accountService.register(registerFail);
        Assert.assertEquals("Registration error", resultFail.getError());
    }

    @Test
    public void recoveryTest() throws MessagingException, UnsupportedEncodingException {
        Email eMail = new Email();
        eMail.setEmail("test@email.com");
        Assert.assertEquals("ok", accountService.recovery(eMail).getData().getMessage());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void changePasswordTest() {
        Response<MessageResponse> result = accountService.changePassword("", "987654321");
        Person editedPerson = personRepository.findByEMail("test@email.com");
        Assert.assertEquals(true, passwordEncoder.matches("987654321", editedPerson.getPassword()));
        Assert.assertEquals("ok", result.getData().getMessage());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void changeEmailTest() {
        Response<MessageResponse> result = accountService.changeEmail("test3@email.com");
        Person editedPerson = personRepository.findByEMail("test3@email.com");
        Assert.assertEquals(person.getId(), editedPerson.getId());
        Assert.assertEquals(person.getFirstName(), editedPerson.getFirstName());
        Assert.assertEquals(person.getLastName(), editedPerson.getLastName());
        Assert.assertEquals("test3@email.com", editedPerson.getEMail());
        Assert.assertEquals("ok", result.getData().getMessage());
    }

    @After
    public void cleanUp(){
        Person testPerson = personRepository.findByEMail("test2@email.com");
        if (testPerson != null) {
            List<NotificationSettings> notifications = notificationSettingsRepository.findByPersonId(testPerson);
            for (NotificationSettings notification : notifications) {
                notificationSettingsRepository.delete(notification);
            }
            personRepository.delete(testPerson);
        }

        Person testPerson2 = personRepository.findByEMail("test3@email.com");
        if (testPerson2 != null) {
            testPerson2.setEMail("test@email.com");
            personRepository.saveAndFlush(testPerson2);
        }

        Person testPerson3 = personRepository.findByEMail("test@email.com");
        if (testPerson3 != null) {
            testPerson3.setPassword(passwordEncoder.encode("123456789"));
            personRepository.saveAndFlush(testPerson3);
        }
    }
}
