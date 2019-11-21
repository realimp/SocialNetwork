package ru.skillbox.socialnetwork.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.api.requests.Email;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private Person person;

    @Autowired
    private AccountService accountService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private EMailService eMailService;

    @Before
    public void setUp() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        person = new Person();
        person.setConfirmationCode("Code123");
        person.setFirstName("firstName");
        person.setEMail("test@email.com");
        person.setPassword("pass1");
        person.setLastName("lastName");
        person.setApproved(true);
        person.setBlocked(false);
        person.setDeleted(false);
        person.setOnline(false);
        person.setPhone("");

        String eMail = person.getEMail();

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContext.setAuthentication(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(eMail);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(accountService.getCurrentUser()).thenReturn(person);

        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(personRepository.findByEMail(eMail)).thenReturn(person);
        Mockito.when(passwordEncoder.encode(person.getPassword())).thenReturn(person.getPassword());
    }

    @Test
    public void registerTest() throws JsonProcessingException {
        
        // тесты register при верных данных
        Register register = new Register();
        register.setEmail("test@email.com");
        register.setPasswd1("pass1");
        register.setPasswd2("pass1");
        register.setFirstName("firstName");
        register.setLastName("lastName");
        register.setCode("code123");
        person = personRepository.save(person);

        //проверяем, что ответ 'ok'
        Response<MessageResponse> result = accountService.register(register);
        Mockito.verify(personRepository, Mockito.times(1)).save(person);
        Assert.assertEquals("ok", result.getData().getMessage());
    }

    @Test
    public void registerFailTest() {
        // тесты register при неверных данных
        Register registerFail = new Register();
        registerFail.setEmail("test@email.com");
        registerFail.setPasswd1("pass1");
        registerFail.setPasswd2("pass2");
        registerFail.setFirstName("firstName");
        registerFail.setLastName("lastName");
        registerFail.setCode("code123");

        // проверяем, что при разных паролях кидаем правильную ошибку
        Response<MessageResponse> resultFail = accountService.register(registerFail);
        Assert.assertEquals("Error by registry", resultFail.getError());

        // проверяем, что не вызывается personRepository.save(person)
        accountService.register(registerFail);
        Mockito.verify(personRepository, Mockito.never()).save(person);
    }

    @Test
    public void recoveryTest() throws MessagingException, UnsupportedEncodingException {

        Email eMail = new Email();
        eMail.setEmail(person.getEMail());
        String mailText = "You password has been changed to ";
        //String eMail = person.getEMail();

        //проверяем, что ответ 'ok', при отправке email
        Mockito.when(eMailService.sendEmail("JavaPro2.SkillBox@mail.ru",
                eMail.getEmail(), "recoveryPassword", mailText)).thenReturn(true);

        Response<MessageResponse> result = accountService.recovery(eMail);
        Assert.assertEquals("ok", result.getData().getMessage());


        //проверяем, что есть обрашение к методу отправки почты
        Mockito.verify(eMailService, Mockito.times(1))
                .sendEmail(
                        ArgumentMatchers.eq("JavaPro2.SkillBox@mail.ru"),
                        ArgumentMatchers.eq(eMail.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());

        //проверяем, что ответ 'error', т.к. email не был отправлен
        Mockito.when(eMailService.sendEmail("JavaPro2.SkillBox@mail.ru",
                eMail.getEmail(), "recoveryPassword", mailText)).thenReturn(false);

        Response<MessageResponse> resultFail = accountService.recovery(eMail);
        String error = "Error by recovery";
        Assert.assertEquals(error, resultFail.getError());

    }

    @Test
    public void changePasswordTest() {
        String newPass = "newPass";

        Mockito.when(passwordEncoder.encode(newPass)).thenReturn(newPass);
        person.setPassword(newPass);

        //проверяем, что ответ 'ok', при верных паролях
        Response<MessageResponse> result = accountService.changePassword("token", newPass);
        Assert.assertEquals("ok", result.getData().getMessage());

        // проверяем, что при ответе ok, есть обращение к personRepository.save(person)
        Mockito.verify(personRepository, Mockito.times(1)).save(person);

        //проверяем, что ответ 'error' при разных паролях
        Response<MessageResponse> resultFail = accountService.changePassword("token", "pass");
        String error = "Error by changing Password";
        Assert.assertEquals(error, resultFail.getError());
    }

    @Test
    public void changeEmailTest() {
        String eMail = person.getEMail();

        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(personRepository.findByEMail(eMail)).thenReturn(person);

        //проверяем, что ответ 'ok'
        Response<MessageResponse> result = accountService.changeEmail(eMail);
        Assert.assertEquals("ok", result.getData().getMessage());

        // проверяем, что при ответе ok, есть обращение к personRepository.save(person)
        Mockito.verify(personRepository, Mockito.times(1)).save(person);

        //проверяем, что ответ 'error' null
        Assert.assertNull(result.getError());
    }
}
