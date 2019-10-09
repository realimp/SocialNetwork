package ru.skillbox.socialnetwork.services;

import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseMessage;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.AccountRepository;

import javax.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

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
        accountRepository.saveAndFlush(person);
        ResponseMessage message = new ResponseMessage();
        message.setMessage("ok");
        Response<ResponseMessage> response = new Response<>(message);
        return response;
    }

    public Response<ResponseMessage> recovery(String email) {
        EMailService eMailService = new EMailService();
        String link = "link";
        if (eMailService.sendEMail("JavaPro2.SkillBox@mail.ru", email, "recoveryPassword", link)) {
            ResponseMessage message = new ResponseMessage();
            Response<ResponseMessage> response = new Response<>(message);
            return response;
        } else {
            ResponseMessage message = new ResponseMessage();
            message.setMessage("");
            Response<ResponseMessage> response = new Response("invalid_request", System.currentTimeMillis(), message);
            return response;
        }
    }

    public Response<ResponseMessage> changePassword(String token, String password) {
        //token ?
        return null;
    }

}
