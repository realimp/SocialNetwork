package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class AccountService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EMailService eMailService;

    private String ABC = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public Response register(Register register) {

        MessageResponse message = new MessageResponse();

        if (!register.getPasswd1().equals(register.getPasswd2())) {
            message.setMessage("Passwords are not equal! Пароли не идентичны!");
            return new Response(message);
        }

        Person person = new Person();
        person.setConfirmationCode(register.getCode());
        person.setFirstName(register.getFirstName());
        person.setEMail(register.getEmail());
        person.setPassword(passwordEncoder.encode(register.getPasswd1()));
        person.setLastName(register.getLastName());
        personRepository.save(person);
        message.setMessage("ok");
        return new Response(message);
    }

    public Response recovery(String email) throws UnsupportedEncodingException, MessagingException {

        int count = (int) (Math.random() * 5) + 6;
        StringBuilder newPas = new StringBuilder();

        for (int i = 0; i < count; i++)
            newPas.append(ABC.charAt((int) (Math.random() * ABC.length())));

        Person person = getCurrentUser();
        person.setPassword(passwordEncoder.encode(newPas.toString()));
        personRepository.save(person);

        String mailText = "You password has been changed to " + newPas.toString();

        MessageResponse message = new MessageResponse();
        if (eMailService.sendEmail("JavaPro2.SkillBox@mail.ru", email, "recoveryPassword", mailText)) {
            message.setMessage("ok");
        } else {
            message.setMessage("");
        }
        return new Response(message);
    }

    public Response changePassword(String token, String password) {
        Person person = getCurrentUser();
        person.setPassword(passwordEncoder.encode(password));
        person = personRepository.save(person);
        MessageResponse message = new MessageResponse();
        if (person.getPassword().equals(password)) {
            message.setMessage("ok");
        } else {
            message.setMessage("");
        }
        return new Response(message);
    }

    public Response changeEmail(String email) {
        Person person = getCurrentUser();
        person.setEMail(email);
        person = personRepository.save(person);
        if (person.getEMail().equals(email)) {
            MessageResponse message = new MessageResponse();
            message.setMessage("ok");
            return new Response(message);
        } else {
            MessageResponse message = new MessageResponse();
            message.setMessage("");
            return new Response(message);
        }
    }

    public Response getNotification() {
        return null;
    }

    public Response setNotification() {
        return null;
    }

    public Person getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return  personRepository.findByEMail(email);
    }
}
