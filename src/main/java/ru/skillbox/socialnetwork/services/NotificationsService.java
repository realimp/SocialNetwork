package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.Notification;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.NotificationRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NotificationsService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AccountService accountService;

    public List<NotificationBase> notificationsForCurrentUser() {

        Person person = accountService.getCurrentUser();

        List<NotificationBase> notificationBaseList = new ArrayList<>();

        List<Notification> notifications = notificationRepository.findByPersonId(person);

        for (Notification notification : notifications) {

            if (!notification.isViewed() && !notification.isDeleted()) {
                notificationBaseList.add(getNotificationBase(notification));
            }
        }

        return notificationBaseList;
    }

    public List<NotificationBase> notificationsMarkAsRead(Integer id, Boolean all) {
        id = id != null ? id : 0;
        all = all != null ? all : true;

        Person person = accountService.getCurrentUser();

        List<NotificationBase> notificationBaseList = new ArrayList<>();

        List<Notification> notifications = notificationRepository.findByPersonId(person);

        for (Notification notification : notifications) {
            if (!notification.isViewed() && !notification.isDeleted()) {
                notificationBaseList.add(getNotificationBaseMarkAsRead(notification, id, all));
            }
        }

        return notificationBaseList;
    }

    //mapping Notification to NotificationBase
    public NotificationBase getNotificationBase(Notification notification) {

        NotificationBase notificationBase = new NotificationBase();
        BasicPerson basicPerson = new BasicPerson();

        notificationBase.setId(notification.getId());
        notificationBase.setEventType(notification.getTypeId());
        notificationBase.setSentTime(notification.getSentDate().getTime());
        notificationBase.setEntityId(notification.getEntityId());

        basicPerson.setId(notification.getAuthor().getId());
        basicPerson.setFirstName(notification.getAuthor().getFirstName());
        basicPerson.setLastName(notification.getAuthor().getLastName());
        String photo = notification.getAuthor().getPhoto() != null ? notification.getAuthor().getPhoto() : "";
        basicPerson.setPhoto(photo);
        if (notification.getAuthor().getLastOnlineTime() != null) {
            basicPerson.setLastOnlineTime(notification.getAuthor().getLastOnlineTime().getTime());
        }
        notificationBase.setEntityAuthor(basicPerson);
        notificationBase.setInfo(notification.getContact());

        return notificationBase;
    }

    //mapping Notification to NotificationBase with mark as read
    public NotificationBase getNotificationBaseMarkAsRead(Notification notification, Integer id, boolean all) {

        NotificationBase notificationBase = new NotificationBase();
        BasicPerson basicPerson = new BasicPerson();

        notificationBase.setId(notification.getId());
        notificationBase.setEventType(notification.getTypeId());
        notificationBase.setSentTime(notification.getSentDate().getTime());
        notificationBase.setEntityId(notification.getEntityId());

        basicPerson.setId(notification.getAuthor().getId());
        basicPerson.setFirstName(notification.getAuthor().getFirstName());
        basicPerson.setLastName(notification.getAuthor().getLastName());
        String photo = notification.getAuthor().getPhoto() != null ? notification.getAuthor().getPhoto() : "";
        basicPerson.setPhoto(photo);
        if (notification.getAuthor().getLastOnlineTime() != null) {
            basicPerson.setLastOnlineTime(notification.getAuthor().getLastOnlineTime().getTime());
        }

        notificationBase.setEntityAuthor(basicPerson);
        notificationBase.setInfo(notification.getContact());

        if (all) {
            notification.setViewed(true);
            notificationRepository.save(notification);
        } else {
            if (notification.getId() == id) {
                notification.setViewed(true);
                notificationRepository.save(notification);
            }
        }

        return notificationBase;
    }
}
