package ru.skillbox.socialnetwork.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.requests.profile.Me;
import ru.skillbox.socialnetwork.api.requests.profile.Wall;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.api.responses.profile.UserDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ProfileController {

    //mapping for a current user
    @GetMapping("/me")
    public Response<PersonDto> getMe() {
        return new Response<>(new PersonDto());
    }

    @PutMapping("/me")
    public Response<PersonDto> putMe(@RequestBody Me me) {
        return new Response<>(new PersonDto());
    }

    @DeleteMapping("/me")
    public Response<MessageDto> deleteMe() {
        return new Response<>(new MessageDto());
    }

    //get user by id
    @GetMapping("/{id}")
    public Response<UserDto> getUser(@PathVariable int id) {
        return new Response<>(new UserDto());
    }

    //getting posts on the user's wall
    @GetMapping("/{id}/wall")
    public ResponseList<List<PersonsWallPostDto>> getUserWall(@PathVariable int id) {
        List<PersonsWallPostDto> personsWallPostDtoList = new ArrayList<>();
        personsWallPostDtoList.add(new PersonsWallPostDto());
        return new ResponseList<>(personsWallPostDtoList);
    }

    //adding a post to a user's wall
    @PostMapping("/{id}/wall")
    public Response<PostDto> postUserWall(@PathVariable int id, @RequestBody Wall wall) {
        return new Response<>(new PostDto());
    }

    //user Search
    @GetMapping("/search")
    public ResponseList<List<PersonDto>> getUserSearch(String firstName, String lastName,
                                                       int ageFrom, int ageTo,
                                                       Integer countryId, Integer cityId,
                                                       int offset, int itemPerPage) {

        List<PersonDto> personsDtoList = new ArrayList<>();
        personsDtoList.add(new PersonDto());
        return new ResponseList<>(personsDtoList);
    }

    //block user by id
    @PutMapping("/block/{id}")
    public Response<MessageDto> blockUser(@PathVariable int id) {
        MessageDto messageDto = new MessageDto();
        return new Response<>(messageDto);
    }

    //unblock user by id
    @DeleteMapping("/block/{id}")
    public Response<MessageDto> unblockUser(@PathVariable int id) {
        MessageDto messageDto = new MessageDto();
        return new Response<>(messageDto);
    }
}
