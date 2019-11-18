package ru.skillbox.socialnetwork.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.entities.Friendship;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.mappers.PersonMapper;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private PersonRepository personRepository;

    public ResponseList<List<PersonResponse>> getFriends(Person person, FriendshipStatus friendshipStatus) {
        logger.info("Получение друзей пользователя {}", person.getEMail());
        List<Friendship> friends = friendshipRepository.findByFriends(person, friendshipStatus);
        logger.info("Количество друзей пользователя {} - {}", person.getEMail(), friends.size());
        List<PersonResponse> friendsResponse = new ArrayList<>();
        friends.forEach(f -> friendsResponse.add(PersonMapper.getMapping(f.getSrcPerson())));
        return new ResponseList<>(friendsResponse, friendsResponse.size());
    }


    public ResponseList<List<PersonResponse>> getRecommendations(Person person) {
        logger.info("Получение рекомендаций для пользователя {}", person.getEMail());
        List<Integer> recommendations = friendshipRepository.findRecommendations(person.getId());
        List<PersonResponse> recommendationsResponse = new ArrayList<>();
        recommendations.forEach(r -> recommendationsResponse.add(PersonMapper.getMapping(personRepository.getOne(r))));
        return new ResponseList<>(recommendationsResponse, recommendationsResponse.size());
    }

    public String deleteFriends(Person user, int friendId) {
        Optional<Person> friend = personRepository.findById(friendId);
        if (!friend.isPresent()) return "Не удалось определить пользователя с идентификатором " + friendId;
        Person fPerson = friend.get();
        Friendship friendship = friendshipRepository.findByFriend(user, fPerson);
        if (friendship == null) return "Пользователь " + fPerson.getEMail() + " не является другом для пользователя " + user.getEMail();
        friendshipRepository.deleteById(friendship.getId());
        return null;
    }

    public String addFriends(Person user, int friendId) {
        Optional<Person> person = personRepository.findById(friendId);
        if (!person.isPresent()) return "Не удалось определить пользователя с идентификатором " + friendId;
        Person friend = person.get();
        Friendship existFriendship = friendshipRepository.findByFriend(user, friend);
        if (existFriendship != null) return "Пользователь " + friend.getEMail() + " уже является другом для пользователя " + user.getEMail();;
        Friendship friendship = new Friendship(user, friend, FriendshipStatus.FRIEND);
        friendship = friendshipRepository.saveAndFlush(friendship);
        if (friendship.getId() == null) return "Пользователь " + friend.getEMail() + " не добавлен другом для пользователя " + user.getEMail();
        return null;
    }

    public FriendshipStatus getFriendship(Person user, int friendId) {
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
        };
        return friendship.getCode();
    }
}