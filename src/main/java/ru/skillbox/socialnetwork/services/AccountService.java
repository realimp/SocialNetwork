package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseMessage;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private PersonRepository personRepository;
    private String ABC = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";


    @Transactional
    public Response<ResponseMessage> register(String email, String passwd1, String passwd2, String firstName, String lastName, String code) {
        if (!passwd1.equals(passwd2)) {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("");
            Response<ResponseMessage> response = new Response("invalid_request", System.currentTimeMillis(), message);
            return response;
        }
        Person person = new Person();
        person.setConfirmationCode(code);
        person.setFirstName(firstName);
        person.setEMail(email);
        person.setPassword(passwd1);
        person.setLastName(lastName);
        personRepository.saveAndFlush(person);
        ResponseMessage message = new ResponseMessage();
        message.setMessage("ok");
        Response<ResponseMessage> response = new Response<>(message);
        return response;
    }

    @Transactional
    public Response<ResponseMessage> recovery(String email) {

        int count = (int) (Math.random() * 5) + 6;
        StringBuilder newPas = new StringBuilder();

        for (int i = 0; i < count; i++)
            newPas.append(ABC.charAt((int) (Math.random() * ABC.length())));

        Person person = getPersonFromSecurity();
        person.setPassword(newPas.toString());

        personRepository.saveAndFlush(person);
        EMailService eMailService = new EMailService();
        String mailText = "You password has been changed to " + newPas.toString();

        if (eMailService.sendEMail("JavaPro2.SkillBox@mail.ru", email, "recoveryPassword", mailText)) {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("ok");
            Response<ResponseMessage> response = new Response<>(message);
            return response;
        } else {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("");
            Response<ResponseMessage> response = new Response("invalid_request", System.currentTimeMillis(), message);
            return response;
        }
    }

    @Transactional
    public Response<ResponseMessage> changePassword(String token, String password) {
        Person person = getPersonFromSecurity();
        person.setPassword(password);
        person = personRepository.saveAndFlush(person);
        if (person.getPassword().equals(password)) {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("ok");
            Response<ResponseMessage> response = new Response<>(message);
            return response;
        } else {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("");
            Response<ResponseMessage> response = new Response("invalid_request", System.currentTimeMillis(), message);
            return response;
        }
    }

    @Transactional
    public Response<ResponseMessage> changeEmail(String email) {
        Person person = getPersonFromSecurity();
        person.setEMail(email);
        person = personRepository.saveAndFlush(person);
        if (person.getEMail().equals(email)) {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("ok");
            Response<ResponseMessage> response = new Response<>(message);
            return response;
        } else {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("");
            Response<ResponseMessage> response = new Response("invalid_request", System.currentTimeMillis(), message);
            return response;
        }
    }

    public Response<ResponseMessage> getNotification() {
        return null;
    }

    public Response<ResponseMessage> setNotification() {
        return null;
    }


    private Person getPersonFromSecurity() {
        //TODO take person from spring security
        return null;
    }

}
