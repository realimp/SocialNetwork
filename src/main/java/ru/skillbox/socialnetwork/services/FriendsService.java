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

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FriendshipRepository friendshipRepository;

    public ResponseList<List<PersonResponse>> getFriends(Person person) {
        logger.info("Получение друзей пользователя {}", person.getEMail());
        List<Friendship> friends = friendshipRepository.findByFriends(person, FriendshipStatus.FRIEND);
        logger.info("Количество друзей пользователя {} - {}", person.getEMail(), friends.size());
        List<PersonResponse> friendsResponse = new ArrayList<>();
        friends.forEach(f -> friendsResponse.add(PersonMapper.getMapping(f.getSrcPerson())));
        return new ResponseList<>(friendsResponse);
    }
}
