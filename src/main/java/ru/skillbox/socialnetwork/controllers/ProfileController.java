package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.services.ProfileService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    //mapping for a current user
    @GetMapping("/me")
    public Response<PersonResponse> getMe() {
        PersonResponse currentPerson = profileService.getPerson();
        return new Response<>(currentPerson);
    }

    @PutMapping("/me")
    public Response<PersonResponse> putMe(@RequestBody PersonResponse personResponse) {
        return new Response<>(new PersonResponse());
    }

    @DeleteMapping("/me")
    public Response<MessageResponse> deleteMe() {
        return new Response<>(new MessageResponse());
    }

    //get user by id
    @GetMapping("/{id}")
    public Response<PersonResponse> getUser(@PathVariable Integer id) {
        return new Response<>(new PersonResponse());
    }

    //getting posts on the user's wall
    @GetMapping("/{id}/wall")
    public ResponseList<List<PersonsWallPost>> getUserWall(@PathVariable Integer id) {
        List<PersonsWallPost> personsWallPostList = new ArrayList<>();
        personsWallPostList.add(new PersonsWallPost());
        return new ResponseList<>(personsWallPostList);
    }

    //adding a post to a user's wall
    @PostMapping("/{id}/wall")
    public Response<PostResponse> postUserWall(@PathVariable Integer id, @RequestBody PersonsWallPost personsWallPost) {
        return new Response<>(new PostResponse());
    }

    //user Search
    @GetMapping("/search")
    public ResponseList<List<PersonResponse>> getUserSearch(String firstName, String lastName,
                                                            Integer ageFrom, Integer ageTo,
                                                            Integer countryId, Integer cityId,
                                                            Integer offset, Integer itemPerPage) {

        List<PersonResponse> personsDtoList = new ArrayList<>();
        personsDtoList.add(new PersonResponse());
        return new ResponseList<>(personsDtoList);
    }

    //block user by id
    @PutMapping("/block/{id}")
    public Response<MessageResponse> blockUser(@PathVariable Integer id) {
        MessageResponse messageResponse = new MessageResponse();
        return new Response<>(messageResponse);
    }

    //unblock user by id
    @DeleteMapping("/block/{id}")
    public Response<MessageResponse> unblockUser(@PathVariable Integer id) {
        MessageResponse messageResponse = new MessageResponse();
        return new Response<>(messageResponse);
    }
}
