package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;
import ru.skillbox.socialnetwork.api.requests.EditPerson;
import ru.skillbox.socialnetwork.api.requests.PostRequest;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.services.PostService;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.AccountService;
import ru.skillbox.socialnetwork.services.ProfileService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private PostService postService;

    //mapping for a current user
    @GetMapping("/me")
    public Response<PersonResponse> getMe() {
        PersonResponse personResponse = profileService.getPerson();
        return new Response<>(personResponse);
    }

    @PutMapping("/me")
    public Response putMe(@RequestBody EditPerson editPerson) {
        PersonResponse responseData = profileService.editPerson(editPerson);
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @DeleteMapping("/me")
    public Response<MessageResponse> deleteMe() {
        MessageResponse messageResponse = profileService.deletePerson();
        return new Response<>(messageResponse);
    }

    //get user by id
    @GetMapping("/{id}")
    public Response<PersonResponse> getUser(@PathVariable Integer id) {
        PersonResponse personResponse = profileService.getPersonById(id);
        return new Response<>(personResponse);
    }

    //getting posts on the user's wall
    @GetMapping("/{id}/wall")
    public ResponseList<List<PersonsWallPost>> getUserWall(@PathVariable Integer id, @PathVariable Integer offset, @PathVariable Integer itemPerPage) {
        List<PersonsWallPost> personsWallPostList = profileService.getWallPostsById(id, offset, itemPerPage);
        personsWallPostList.add(new PersonsWallPost());
        return new ResponseList<>(personsWallPostList);
    }

    //adding a post to a user's wall
    @PostMapping("/{id}/wall")
    public Response<PostResponse> postUserWall(@PathVariable Integer id, @RequestParam(value = "publish_date", required = false) Long publishDate, @RequestBody PostRequest postRequest) {

        if (publishDate == null) publishDate = System.currentTimeMillis();
        postService.addPost(id,new Date(publishDate),postRequest);
        //ToDo после того как мы пост добавили нам нужно что то возвращать на фронт или просто редирект на главную страницу?
        PostResponse postResponse = profileService.addWallPostById(id, new Date(publishDate));
        return new Response<>(postResponse);
    }

    //user Search
    @GetMapping("/search")
    public ResponseList<List<PersonResponse>> getUserSearch(@PathVariable String firstName, @PathVariable String lastName,
                                                            @PathVariable Integer ageFrom, @PathVariable Integer ageTo,
                                                            @PathVariable String country, @PathVariable String city,
                                                            @PathVariable Integer offset, @PathVariable Integer itemPerPage) {

        List<PersonResponse> personResponseList = profileService.searchPerson(firstName, lastName, ageFrom, ageTo,
                country, city, offset, itemPerPage);
        return new ResponseList<>(personResponseList);
    }

    //block user by id
    @PutMapping("/block/{id}")
    public Response<MessageResponse> blockUser(@PathVariable Integer id) {
        MessageResponse messageResponse = profileService.blockPersonById(id);
        return new Response<>(messageResponse);
    }

    //unblock user by id
    @DeleteMapping("/block/{id}")
    public Response<MessageResponse> unblockUser(@PathVariable Integer id) {
        MessageResponse messageResponse = profileService.blockPersonById(id);
        return new Response<>(messageResponse);
    }
}
