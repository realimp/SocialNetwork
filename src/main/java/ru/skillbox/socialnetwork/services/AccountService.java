package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private PersonRepository personRepository;
    private String ABC = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public MessageResponse register(Register register) {

        MessageResponse message = new MessageResponse();

        if (!register.getPasswd1().equals(register.getPasswd2())) {
            message.setMessage("Wrong password!");
            return message;
        }

        Person person = new Person();
        person.setConfirmationCode(register.getCode());
        person.setFirstName(register.getFirstName());
        person.setEMail(register.getEmail());
        person.setPassword(register.getPasswd1());
        person.setLastName(register.getLastName());
        personRepository.saveAndFlush(person);
        message.setMessage("ok");
        return message;
    }

    public MessageResponse recovery(String email) {

        int count = (int) (Math.random() * 5) + 6;
        StringBuilder newPas = new StringBuilder();

        for (int i = 0; i < count; i++)
            newPas.append(ABC.charAt((int) (Math.random() * ABC.length())));

        Person person = getCurrentUser();
        person.setPassword(newPas.toString());
        personRepository.saveAndFlush(person);

        EMailService eMailService = new EMailService();
        String mailText = "You password has been changed to " + newPas.toString();

        MessageResponse message = new MessageResponse();
//        if (eMailService.sendEMail("JavaPro2.SkillBox@mail.ru", email, "recoveryPassword", mailText)) {
//            message.setMessage("ok");
//        } else {
//            message.setMessage("");
//        }
        return message;
    }

    public MessageResponse changePassword(String token, String password) {
        Person person = getCurrentUser();
        person.setPassword(password);
        person = personRepository.saveAndFlush(person);
        MessageResponse message = new MessageResponse();
        if (person.getPassword().equals(password)) {
            message.setMessage("ok");
        } else {
            message.setMessage("");
        }
        return message;
    }

    public MessageResponse changeEmail(String email) {
        Person person = getCurrentUser();
        person.setEMail(email);
        person = personRepository.saveAndFlush(person);
        if (person.getEMail().equals(email)) {
            MessageResponse message = new MessageResponse();
            message.setMessage("ok");
            return message;
        } else {
            MessageResponse message = new MessageResponse();
            message.setMessage("");
            return message;
        }
    }

    public MessageResponse getNotification() {
        return null;
    }

    public MessageResponse setNotification() {
        return null;
    }

    public Person getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return  personRepository.findByEMail(email);
    }
}
