package ru.skillbox.socialnetwork.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.UserAuthorization;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private EMailService eMailService;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    @MockBean
    private UserAuthorization authorization;

    @Test
    public void registerTest() throws JsonProcessingException {

        Register registerOk = new Register();
        registerOk.setEmail("test@email.com");
        registerOk.setPasswd1("pass1");
        registerOk.setPasswd2("pass1");
        registerOk.setFirstName("firstName");
        registerOk.setLastName("lastName");
        registerOk.setCode("code123");

        Person person = new Person();
        person.setPassword(registerOk.getPasswd1());
        person.setFirstName(registerOk.getFirstName());
        person.setLastName(registerOk.getLastName());
        person.setEMail(registerOk.getEmail());
        person.setConfirmationCode(registerOk.getCode());

        System.out.println(new ObjectMapper().writeValueAsString(person));

        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(passwordEncoder.encode("pass1")).thenReturn("pass1");

        accountService.register(registerOk);

        System.out.println(new ObjectMapper().writeValueAsString(person));

        Mockito.verify(personRepository, Mockito.times(1)).save(person);
        Assert.assertEquals("lastName", personRepository.findByEMail("test@email.com").getLastName());

        Register register = new Register();
        register.setEmail("test@email.com");
        register.setPasswd1("pass1");
        register.setPasswd2("pass2");

        accountService.register(register);
        Mockito.verify(personRepository, Mockito.never()).save(person);
    }

    @Test
    public void recovery() throws MessagingException, JsonProcessingException, UnsupportedEncodingException {
        Person person = new Person();
        person.setPassword("123");
        person.setFirstName("Ivanov");
        person.setLastName("Petr");
        person.setEMail("some@mail");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContext.setAuthentication(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn("some@mail");
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(accountService.getCurrentUser()).thenReturn(person);

        System.out.println(new ObjectMapper().writeValueAsString(accountService.getCurrentUser()));

        accountService.recovery("some@mail");

                Mockito.verify(eMailService, Mockito.times(1))
                .sendEmail(
                        ArgumentMatchers.eq("JavaPro2.SkillBox@mail.ru"),
                        ArgumentMatchers.eq("some@mail"),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void changePassword() throws JsonProcessingException {
        Person person = new Person();
        person.setPassword("123");
        person.setFirstName("Ivanov");
        person.setLastName("Petr");
        person.setEMail("some@mail.com");
        person.setPassword("somePass");
        String newPass = "changePass";


        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContext.setAuthentication(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn("some@mail.com");
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(accountService.getCurrentUser()).thenReturn(person);
        Mockito.when(passwordEncoder.encode(newPass)).thenReturn(newPass);

        System.out.println(new ObjectMapper().writeValueAsString(accountService.getCurrentUser()));

        person.setPassword(newPass);
        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(personRepository.findByEMail("some@mail.com")).thenReturn(person);

        System.out.println(new ObjectMapper().writeValueAsString(accountService.getCurrentUser()));

        accountService.changePassword("token", newPass);

        Mockito.verify(personRepository, Mockito.times(1)).save(person);
        Assert.assertEquals(newPass, personRepository.findByEMail("some@mail.com").getPassword());
    }

    @Test
    public void changeEmail() throws JsonProcessingException {
        Person person = new Person();
        person.setPassword("123");
        person.setFirstName("Ivanov");
        person.setLastName("Petr");
        person.setEMail("some@mail");
        String newMail = "change@mail.com";

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContext.setAuthentication(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn("some@mail");
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(accountService.getCurrentUser()).thenReturn(person);

        System.out.println(new ObjectMapper().writeValueAsString(accountService.getCurrentUser()));

        person.setEMail(newMail);
        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(personRepository.findByEMail(newMail)).thenReturn(person);

        System.out.println(new ObjectMapper().writeValueAsString(accountService.getCurrentUser()));

        accountService.changeEmail(newMail);
        Mockito.verify(personRepository, Mockito.times(1)).save(person);
        Assert.assertEquals("change@mail.com", personRepository.findByEMail("change@mail.com").getEMail());
    }
}
