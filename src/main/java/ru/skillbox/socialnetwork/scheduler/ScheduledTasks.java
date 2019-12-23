package ru.skillbox.socialnetwork.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
import ru.skillbox.socialnetwork.repositories.NotificationRepository;
import ru.skillbox.socialnetwork.repositories.NotificationSettingsRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    private DateFormat df = new SimpleDateFormat("dd.MM");

    //@Scheduled(cron = "*/50 * * * * *")
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void birthdayNotification() {
        System.out.println("SCHEDULER WORKING birthdayNotification" + new Date());

        int pageOffset = 0;
        Pageable page = PageRequest.of(pageOffset++, 20);
        Page<NotificationSettings> enabledNotificationsPage = notificationSettingsRepository.findByTypeIdAndEnabled(5, page);

        while (enabledNotificationsPage.hasNext()) {
            for (NotificationSettings setting : enabledNotificationsPage) {
                Person person = setting.getPerson();

                int offset = 0;
                Pageable pageable = PageRequest.of(offset++, 20);
                Page<Friendship> friendships = friendshipRepository.findByFriends(person, FriendshipStatus.FRIEND, pageable);

                while (friendships.hasNext()) {
                    for (Friendship friendship : friendships) {
                        Person friend = friendship.getSrcPerson().equals(person) ? friendship.getDstPerson() : friendship.getSrcPerson();
                        if (df.format(friend.getBirthDate()).equals(df.format(new Date()))) {
                            Notification notification = new Notification();
                            notification.setTypeId(NotificationTypeCode.FRIEND_BIRTHDAY);
                            notification.setAuthor(friend);
                            notification.setRecipient(person);
                            notification.setEntityId(1);
                            notification.setContact(friend.getEMail());
                            notification.setViewed(false);
                            notification.setSentDate(new Date());
                            notificationRepository.save(notification);
                        }
                    }
                    pageable = PageRequest.of(offset++, 20);
                    friendships = friendshipRepository.findByFriends(person, FriendshipStatus.FRIEND, pageable);
                }
            }
            page = PageRequest.of(pageOffset++, 20);
            enabledNotificationsPage = notificationSettingsRepository.findByTypeIdAndEnabled(5, page);
        }
    }

    //@Scheduled(cron = "*/80 * * * * *")
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void removeOldNotification() {
        System.out.println("SCHEDULER WORKING removeOldNotification" + new Date());
        for (Notification notification :
                notificationRepository.findBySentTime(new Date(new Date().getTime() - 5 * 86_400_000))) {
            notification.setDeleted(true);
        }
    }
}