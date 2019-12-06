package ru.skillbox.socialnetwork.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
import ru.skillbox.socialnetwork.repositories.NotificationRepository;
import ru.skillbox.socialnetwork.repositories.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Scheduled(cron = "0 0 * * *")//Выполнять ежедневно в полночь
    //@Scheduled(cron = "*/50000 * * * * *")
    @Transactional
    public void birthdayNotification() {
        System.out.println("SCHEDULER WORKING birthdayNotification" + new Date());
        DateFormat df = new SimpleDateFormat("dd.MM");

        List<Person> people = personRepository.findAll();
        List<Person> peopleBirthDay = new ArrayList<>();

        for (Person person : people) {
            if (df.format(person.getBirthDate()).equals(df.format(new Date()))) {
                peopleBirthDay.add(person);
            }
        }

        List<NotificationSettings> notificationSettings;
        Friendship friendshipPerson;

        for (Person person : people) {
            System.out.println("person " + person.getLastName());
            boolean isEnable = false;
            notificationSettings = notificationSettingsRepository.findByPersonId(person);

            for (NotificationSettings settings : notificationSettings) {
                if (settings.getNotificationTypeCode().name().equals("FRIEND_BIRTHDAY")) {
                    isEnable = settings.getEnable();
                }
            }

            if(isEnable) {
                for (Person friend : peopleBirthDay) {
                    friendshipPerson = friendshipRepository.findByFriend(person, friend);
                    if (friendshipPerson != null && friendshipPerson.getCode().name().equals("FRIEND")){
                        System.out.println("person " + person.getLastName());
                        System.out.println("friend " + friend.getLastName());
                        Notification notification = new Notification();
                        notification.setTypeId(NotificationTypeCode.FRIEND_BIRTHDAY);
                        notification.setAuthor(person);
                        notification.setEntityId(1);
                        notification.setContact(friend.getEMail());
                        notification.setViewed(false);
                        notification.setSentDate(new Date());
                        System.out.println("notification " + notification.getSentDate().getTime());
                        System.out.println("notification " + notification.getContact());
                        System.out.println("notification " + notification.getAuthor().getLastName());
                        notificationRepository.save(notification);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0 * * *")//Выполнять ежедневно в полночь
    //@Scheduled(cron = "*/50000 * * * * *")
    @Transactional
    public void removeOldNotification() {
        List<Notification> notifications = notificationRepository.findAll();

        if (notifications.size() > 1) {
            for (Notification notification : notifications) {
                //86_400_000 msec in day
                if ((new Date().getTime() - notification.getSentDate().getTime()) >= 10 * 86_400_000) {
                    notification.setDeleted(true);
                }
            }
        }
    }
}