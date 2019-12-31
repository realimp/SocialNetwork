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

    public Response<MessageResponse> register(Register register) {

        MessageResponse message = new MessageResponse();

        if (!register.getPasswd1().equals(register.getPasswd2()) || register.getPasswd1().isEmpty()) {
            message.setMessage("Пароли не идентичны!");
            String error = "Registration error";
            long timestamp = new Date().getTime();
            return new Response<>(error, timestamp, message);
        }

        if (personRepository.findByEMail(register.getEmail()) != null) {
            message.setMessage("Указанный email уже существует!");
            String error = "Registration error";
            long timestamp = new Date().getTime();
            return new Response<>(error, timestamp, message);
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
        Person savedPerson = personRepository.saveAndFlush(person);
        message.setMessage("ok");

        NotificationTypeCode[] typeCodes = NotificationTypeCode.values();
        for (NotificationTypeCode typeCode : typeCodes) {
            notificationSettingsRepository.save(new NotificationSettings(savedPerson, typeCode, true));
        }

        return new Response<>(message);
    }

    public Response<MessageResponse> recovery(Email email) throws UnsupportedEncodingException, MessagingException {

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

            if (eMailService.sendEmail(email.getEmail(),
                    "recovery Password", "recoveryPassword", mailText)) {

                person.setPassword(passwordEncoder.encode(newPas.toString()));
                personRepository.save(person);

                Response<MessageResponse> response = new Response<>(message);
                response.setTimestamp(timestamp);
                message.setMessage("ok");
                return response;
            } else {
                String error = "Error by recovery";
                message.setMessage(error);
                return new Response<>(error, timestamp, message);
            }
        } else {
            String error = "Wrong e-Mail";
            message.setMessage(error);
            return new Response<>(error, timestamp, message);
        }
    }

    public Response<MessageResponse> changePassword(String token, String password) {
        Person person = getCurrentUser();
        person.setPassword(passwordEncoder.encode(password));
        personRepository.saveAndFlush(person);
        MessageResponse message = new MessageResponse("ok");
        return new Response<>(message);
    }

    public Response<MessageResponse> changeEmail(String email) {
        Person person = getCurrentUser();
        person.setEMail(email);
        personRepository.saveAndFlush(person);
        MessageResponse response = new MessageResponse("ok");
        return new Response<>(response);
    }

    public Response<NotificationSettingsResponse> getNotification() {
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

        return new Response<>(notificationSettings);
    }

    public Response<MessageResponse> setNotification(NotificationTypeRequest notificationCode) {
        MessageResponse message = new MessageResponse("ok");

        Person person = getCurrentUser();

        NotificationTypeCode notificationTypeCode = NotificationTypeCode.valueOf(notificationCode.getType());
        NotificationSettings notificationSettings = notificationSettingsRepository.findByPersonIdAndSettingCode(person, notificationTypeCode.getCode());
        notificationSettings.setEnable(notificationCode.isEnable());

        notificationSettingsRepository.save(notificationSettings);

        return new Response<>(message);
    }

    public Person getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return personRepository.findByEMail(email);
    }
}
