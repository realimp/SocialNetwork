package ru.skillbox.socialnetwork.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.FriendStatus;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.mappers.PersonMapper;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
import ru.skillbox.socialnetwork.repositories.NotificationRepository;
import ru.skillbox.socialnetwork.repositories.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FriendsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;

    @Autowired
    private PersonRepository personRepository;

    public ResponseList<List<PersonResponse>> getFriends(Person person) {
        if (person == null)
            return new ResponseList<>("Не удалось определить пользователя с идентификатором null", null);
        logger.info("Получение друзей пользователя {}", person.getEMail());
        List<Friendship> friends = friendshipRepository.findByFriends(person);
        logger.info("Количество друзей пользователя {} - {}", person.getEMail(), friends.size());
        List<PersonResponse> friendsResponse = new ArrayList<>();
        friends.forEach(f -> friendsResponse.add(PersonMapper.getMapping(f.getSrcPerson())));
        return new ResponseList<>(friendsResponse, friendsResponse.size());
    }

    public ResponseList<List<PersonResponse>> getFriends(Person person, FriendshipStatus friendshipStatus) {
        if (person == null)
            return new ResponseList<>("Не удалось определить пользователя с идентификатором null", null);
        logger.info("Получение друзей пользователя {}", person.getEMail());
        List<Friendship> friends = friendshipRepository.findByFriends(person, friendshipStatus);
        logger.info("Количество друзей пользователя {} - {}", person.getEMail(), friends.size());
        List<PersonResponse> friendsResponse = new ArrayList<>();
        friends.forEach(f -> friendsResponse.add(PersonMapper.getMapping(f.getSrcPerson())));
        return new ResponseList<>(friendsResponse, friendsResponse.size());
    }


    public ResponseList<List<PersonResponse>> getRecommendations(Person person) {
        if (person == null)
            return new ResponseList<>("Не удалось определить пользователя с идентификатором null", null);
        logger.info("Получение рекомендаций для пользователя {}", person.getEMail());
        List<Integer> recommendations = friendshipRepository.findRecommendations(person.getId());
        List<PersonResponse> recommendationsResponse = new ArrayList<>();
        recommendations.forEach(r -> recommendationsResponse.add(PersonMapper.getMapping(personRepository.getOne(r))));
        return new ResponseList<>(recommendationsResponse, recommendationsResponse.size());
    }

    public String deleteFriends(Person user, Person friend) {
        if (user == null) return "Не удалось определить пользователя с идентификатором null";
        Friendship friendship = friendshipRepository.findByFriend(user, friend);
        if (friendship == null)
            return "Пользователь " + friend.getEMail() + " не является другом для пользователя " + user.getEMail();
        friendshipRepository.deleteById(friendship.getId());
        return null;
    }

    public String addFriends(Person user, Person friend) {
        if (user == null) return "Не удалось определить пользователя с идентификатором null";
        Friendship existFriendship = friendshipRepository.findByFriend(user, friend);
        if (existFriendship != null) {
            if (existFriendship.getCode().equals(FriendshipStatus.REQUEST)) {
                existFriendship.setCode(FriendshipStatus.FRIEND);
                friendshipRepository.saveAndFlush(existFriendship);
                return null;
            } else
                return "Пользователь " + friend.getEMail() + " уже является другом для пользователя " + user.getEMail();
        }
        if (user.equals(friend)) return "Пользователь " + friend.getEMail() + " не может быть сам себе другом";
        Friendship friendship = new Friendship(user, friend, FriendshipStatus.REQUEST);
        friendship = friendshipRepository.saveAndFlush(friendship);

        boolean isEnable = false;
        List<NotificationSettings> notificationSettings = notificationSettingsRepository.findByPersonId(user);

        for (NotificationSettings settings : notificationSettings) {
            if (settings.getNotificationTypeCode().name().equals("FRIEND_REQUEST")) {
                isEnable = settings.getEnable();
            }
        }
        if (isEnable) {
            Notification notification = new Notification(NotificationTypeCode.FRIEND_REQUEST, new Date(),
                    friend, user, 1, user.getEMail());
            notificationRepository.save(notification);
        }

        if (friendship.getId() == null)
            return "Пользователь " + friend.getEMail() + " не добавлен другом для пользователя " + user.getEMail();
        return null;
    }

    public FriendshipStatus getFriendship(Person user, int friendId) {
        if (user == null) {
            logger.error("Не удалось определить пользователя с идентификатором null");
            return null;
        }
        Optional<Person> person = personRepository.findById(friendId);
        if (!person.isPresent()) {
            logger.error("Не удалось определить пользователя с идентификатором {}", friendId);
            return null;
        }
        Person friend = person.get();
        Friendship friendship = friendshipRepository.findByFriend(user, friend);
        if (friendship == null) {
            logger.warn("Нет информации для пользователя {} относительно пользователя {}", friend.getEMail(), user.getEMail());
            return null;
        }
        return friendship.getCode();
    }
}
