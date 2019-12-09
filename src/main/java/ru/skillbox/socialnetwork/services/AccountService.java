package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.requests.Email;
import ru.skillbox.socialnetwork.api.requests.NotificationTypeRequest;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.NotificationSettings;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class AccountService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EMailService eMailService;

    private String ABC = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public Response register(Register register) {

        MessageResponse message = new MessageResponse();

        if (!register.getPasswd1().equals(register.getPasswd2())
                || register.getPasswd1().isEmpty()) {
            message.setMessage("Пароли не идентичны!");

            String error = "Error by registry";
            long timestamp = new Date().getTime();

            return new Response(error, timestamp, message);
        }

        if (personRepository.findByEMail(register.getEmail()) != null) {
            message.setMessage("Указанный email уже существует!");
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

        NotificationTypeCode[] typeCodes = NotificationTypeCode.values();
        for (NotificationTypeCode typeCode : typeCodes) {
            notificationSettingsRepository.save(new NotificationSettings(person, typeCode, true));
        }

        long timestamp = new Date().getTime();
        Response response = new Response(message);
        response.setTimestamp(timestamp);
        return response;
    }

    public Response recovery(Email email) throws UnsupportedEncodingException, MessagingException {

        MessageResponse message = new MessageResponse();
        long timestamp = new Date().getTime();

        Person person = personRepository.findByEMail(email.getEmail());

        if (person != null) {

            int count = (int) (Math.random() * 10) + 6;
            StringBuilder newPas = new StringBuilder();

            Random random = new Random();
            for (int i = 0; i < count; i++)
                newPas.append(ABC.charAt((int) (Math.random() * ABC.length()))).append(random.nextInt(100));

            String mailText = "You password has been changed to " + newPas.toString();
            //String mailText = "You password has been changed to ";

            if (eMailService.sendEmail(email.getEmail(),
                    "recovery Password", "recoveryPassword", mailText)) {

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
        } else {
            String error = "Wrong e-Mail";
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
        } else {
            MessageResponse message = new MessageResponse();
            String error = "Error by changing Email";
            message.setMessage(error);
            return new Response(error, timestamp, message);
        }
    }

    public Response getNotification() {
        long timestamp = new Date().getTime();
        MessageResponse message = new MessageResponse();
        message.setMessage("ok");
        Response response = new Response(message);
        response.setTimestamp(timestamp);

        Person person = getCurrentUser();

        List<NotificationSettings> notificationSettingsList = notificationSettingsRepository.findByPersonId(person);

        NotificationTypeCode[] typeCodes = NotificationTypeCode.values();

        List<NotificationParameter> notificationParameters = new ArrayList<>();

        for (NotificationTypeCode notificationTypeCode : typeCodes) {
            boolean isAdded = false;
            for (int i = 0; i < notificationSettingsList.size(); i++) {
                if (notificationSettingsList.get(i).getNotificationTypeCode().equals(notificationTypeCode)) {
                    notificationParameters.add(new NotificationParameter
                            (NotificationTypeCode.valueOf(notificationTypeCode.name()),
                                    notificationSettingsList.get(i).getEnable()));
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                notificationSettingsRepository.save(new NotificationSettings(person, notificationTypeCode, true));
                notificationParameters.add(new NotificationParameter
                        (NotificationTypeCode.valueOf(notificationTypeCode.name()),
                                true));
            }
        }

        NotificationSettingsResponse notificationSettings = new NotificationSettingsResponse();
        notificationSettings.setNotificationSettings(notificationParameters);

        response.setData(notificationSettings.getNotificationSettings());
        return response;
    }

    public Response setNotification(NotificationTypeRequest notificationCode) {
        long timestamp = new Date().getTime();
        MessageResponse message = new MessageResponse();
        message.setMessage("ok");
        Response response = new Response(message);
        response.setTimestamp(timestamp);

        Person person = getCurrentUser();

        NotificationTypeCode notificationTypeCode = NotificationTypeCode.valueOf(notificationCode.getType());
        NotificationSettings notificationSettings = notificationSettingsRepository.findByPersonIdAndSettingCode(person, notificationTypeCode.getCode());
        notificationSettings.setEnable(notificationCode.isEnable());

        notificationSettingsRepository.save(notificationSettings);

        //System.out.println("NEW notificationSettings " + notificationSettings.getNotificationTypeCode() + " - " + notificationSettings.getEnable());

        return response;
    }

    public Person getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return personRepository.findByEMail(email);
    }
}
