package ru.skillbox.socialnetwork.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.AccountService;
import ru.skillbox.socialnetwork.services.FriendsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class FriendsController {

    @Autowired
    private FriendsService friendsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PersonRepository personRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/friends")
    public ResponseList<List<PersonResponse>> getFriends() {
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new ResponseList<>("Не удалось определить текущего пользователя", null);
        return friendsService.getFriends(currentUser);
    }

    @GetMapping("/friends/recommendations")
    public ResponseList<List<PersonResponse>> getRecommendations() {
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new ResponseList<>("Не удалось определить текущего пользователя", null);
        return friendsService.getRecommendations(currentUser);
    }

    @GetMapping("/friends/request")
    public ResponseList<List<PersonResponse>> getFriendsRequest() {
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new ResponseList<>("Не удалось определить текущего пользователя", null);
        return friendsService.getFriends(currentUser, FriendshipStatus.REQUEST);
    }

    @DeleteMapping("/friends/{id}")
    public Response<MessageResponse> deleteFriends(@PathVariable("id") Integer id) {
        if (id == null) return new Response<>("Передан пустой параметр", null);
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new Response<>("Не удалось определить текущего пользователя", null);
        Optional<Person> friend = personRepository.findById(id);
        if (!friend.isPresent())
            return new Response<>("Не удалось определить пользователя с идентификатором " + id, null);
        Person fPerson = friend.get();
        String result = friendsService.deleteFriends(currentUser, fPerson);
        if (result != null) return new Response<>(result, null);
        result = friendsService.deleteFriends(fPerson, currentUser);
        if (result != null) return new Response<>(result, null);
        return new Response<>(new MessageResponse("ok"));
    }

    @PostMapping("/friends/{id}")
    public Response<MessageResponse> addFriends(@PathVariable("id") Integer id) {
        if (id == null) return new Response<>("Передан пустой параметр", null);
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new Response<>("Не удалось определить текущего пользователя", null);
        Optional<Person> person = personRepository.findById(id);
        if (!person.isPresent())
            return new Response<>("Не удалось определить пользователя с идентификатором " + id, null);
        Person friend = person.get();
        String result = friendsService.addFriends(currentUser, friend);
        if (result != null) return new Response<>(result, null);
        result = friendsService.addFriends(friend, currentUser);
        if (result != null) return new Response<>(result, null);
        return new Response<>(new MessageResponse("ok"));
    }

    @PostMapping("/is/friends")
    public DataResponse checkFriends(@RequestBody UserIds userIds) {
        int[] ids = userIds.getIds();
        if (ids == null) return new DataResponse(new ArrayList<>());
        Person currentUser = accountService.getCurrentUser();
        logger.error("Не удалось определить текущего пользователя");
        if (currentUser == null) return new DataResponse(new ArrayList<>());
        List<FriendStatus> friendStatusList = new ArrayList<>();
        for (int id : ids) {
            FriendshipStatus friendshipStatus = friendsService.getFriendship(currentUser, id);
            if (friendshipStatus != null)
                friendStatusList.add(new FriendStatus(id, friendshipStatus));
        }
        return new DataResponse(friendStatusList);
    }
}
