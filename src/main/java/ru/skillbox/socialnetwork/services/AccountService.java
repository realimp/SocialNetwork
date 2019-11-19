package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
import java.util.Date;

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

        // нажо так же проверять что пароли не пустые
        if (!register.getPasswd1().equals(register.getPasswd2())
                || register.getPasswd1().isEmpty()) {
            message.setMessage("Пароли не идентичны!");

            String error = "Error by registry";
            long timestamp = new Date().getTime();

            return new Response(error, timestamp, message);
        }

        Person person = new Person();
        person.setRegDate(new Date());
        person.setConfirmationCode(register.getCode());
        person.setFirstName(register.getFirstName());
        person.setEMail(register.getEmail());
        person.setPassword(passwordEncoder.encode(register.getPasswd1()));
        person.setLastName(register.getLastName());
        person.setApproved(true);
        person.setBlocked(false);
        person.setDeleted(false);
        person.setOnline(false);
        person.setPhone("");
        personRepository.save(person);
        message.setMessage("ok");

        long timestamp = new Date().getTime();
        Response response = new Response(message);
        response.setTimestamp(timestamp);
        return response;
    }

    public Response recovery(String email) throws UnsupportedEncodingException, MessagingException {
        long timestamp = new Date().getTime();

        int count = (int) (Math.random() * 5) + 6;
        StringBuilder newPas = new StringBuilder();

        for (int i = 0; i < count; i++)
            newPas.append(ABC.charAt((int) (Math.random() * ABC.length())));

        Person person = getCurrentUser();

        //String mailText = "You password has been changed to " + newPas.toString();
        String mailText = "You password has been changed to ";

        MessageResponse message = new MessageResponse();
        if (eMailService.sendEmail("JavaPro2.SkillBox@mail.ru",
                email, "recoveryPassword", mailText)) {

            person.setPassword(passwordEncoder.encode(newPas.toString()));
            personRepository.save(person);

            Response response = new Response(message);
            response.setTimestamp(timestamp);
            message.setMessage("ok");
            return response;
        } else {
            String error = "Error by recovery";
            message.setMessage(error);
            return new Response(error, timestamp, message);
        }
    }

    public Response changePassword(String token, String password) {
        long timestamp = new Date().getTime();

        Person person = getCurrentUser();

        MessageResponse message = new MessageResponse();
        if (person.getPassword().equals(password) && !password.isEmpty()) {
            person.setPassword(passwordEncoder.encode(password));
            personRepository.save(person);

            Response response = new Response(message);
            response.setTimestamp(timestamp);
            message.setMessage("ok");
            return response;
        } else {
            String error = "Error by changing Password";
            message.setMessage(error);
            return new Response(error, timestamp, message);
        }
    }

    public Response changeEmail(String email) {
        long timestamp = new Date().getTime();

        Person person = getCurrentUser();
        person.setEMail(email);

        if (person.getEMail().equals(email)) {
            MessageResponse message = new MessageResponse();

            personRepository.save(person);

            Response response = new Response(message);
            response.setTimestamp(timestamp);
            message.setMessage("ok");
            return response;
        }
        else {
            MessageResponse message = new MessageResponse();
            String error = "Error by changing Email";
            message.setMessage(error);
            return new Response(error, timestamp, message);
        }
    }

    public Response getNotification() {
        return null;
    }

    public Response setNotification() {
        return null;
    }

    public Person getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return personRepository.findByEMail(email);
    }
}
