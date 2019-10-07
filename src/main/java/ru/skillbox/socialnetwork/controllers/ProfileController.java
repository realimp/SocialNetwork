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
import ru.skillbox.socialnetwork.api.responses.profile.User;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ProfileController {

    //mapping for a current user
    @GetMapping("/me")
    public Response<Person> getMe() {
        return new Response<>(new Person());
    }

    @PutMapping("/me")
    public Response<Person> putMe(@RequestBody Me me) {
        return new Response<>(new Person());
    }

    @DeleteMapping("/me")
    public Response<Message> deleteMe() {
        return new Response<>(new Message());
    }

    //get user by id
    @GetMapping("/{id}")
    public Response<User> getUser(@PathVariable int id) {
        return new Response<>(new User());
    }

    //getting posts on the user's wall
    @GetMapping("/{id}/wall")
    public ResponseList<List<PersonsWallPost>> getUserWall(@PathVariable int id) {
        List<PersonsWallPost> personsWallPostList = new ArrayList<>();
        personsWallPostList.add(new PersonsWallPost());
        return new ResponseList<>(personsWallPostList);
    }

    //adding a post to a user's wall
    @PostMapping("/{id}/wall")
    public Response<Post> postUserWall(@PathVariable int id, @RequestBody Wall wall) {
        return new Response<>(new Post());
    }

    //user Search
    @GetMapping("/search")
    public ResponseList<List<Person>> getUserSearch(String firstName, String lastName,
                                                    int ageFrom, int ageTo,
                                                    Integer countryId, Integer cityId,
                                                    int offset, int itemPerPage) {

        List<Person> personsDtoList = new ArrayList<>();
        personsDtoList.add(new Person());
        return new ResponseList<>(personsDtoList);
    }

    //block user by id
    @PutMapping("/block/{id}")
    public Response<Message> blockUser(@PathVariable int id) {
        Message message = new Message();
        return new Response<>(message);
    }

    //unblock user by id
    @DeleteMapping("/block/{id}")
    public Response<Message> unblockUser(@PathVariable int id) {
        Message message = new Message();
        return new Response<>(message);
    }
}
