package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.services.AccountService;
import ru.skillbox.socialnetwork.services.FriendsService;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private FriendsService friendsService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseList<List<PersonResponse>> getFriends() {
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new ResponseList<>("Не удалось определить пользователя", null);
        return friendsService.getFriends(currentUser, FriendshipStatus.FRIEND);
    }

    @GetMapping("/recommendations")
    public ResponseList<List<PersonResponse>> getRecommendations() {
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new ResponseList<>("Не удалось определить пользователя", null);
        return friendsService.getRecommendations(currentUser);
    }

    @GetMapping("/request")
    public ResponseList<List<PersonResponse>> getFriendsRequest() {
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new ResponseList<>("Не удалось определить пользователя", null);
        return friendsService.getFriends(currentUser, FriendshipStatus.REQUEST);
    }
}
