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
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseMessage;
import ru.skillbox.socialnetwork.api.responses.profile.ResponseUser;
import ru.skillbox.socialnetwork.entities.Person;

@RestController
@RequestMapping("/users")
public class ProfileController {

  //mapping for a current user
  @GetMapping("/me")
  public Response<Person> getMe() {
    Person person = new Person();
    return new Response<>(person);
  }

  @PutMapping("/me")
  public Response<Person> putMe(@RequestBody Me me) {
    Person person = new Person();
    return new Response<>(person);
  }

  @DeleteMapping("/me")
  public Response<ResponseMessage> deleteMe() {
    ResponseMessage responseMessage = new ResponseMessage();
    return new Response<>(responseMessage);
  }

  //get user by id
  @GetMapping("/{id}")
  public Response<ResponseUser> getUser(@PathVariable int id) {
    ResponseUser responseUser = new ResponseUser();
    return new Response<>(responseUser);
  }

  //getting posts on the user's wall
  @GetMapping("/{id}/wall")
  public String getUserWall(@PathVariable int id) {
    return "User id = " + id + " -> get wall";
  }

  //adding a post to a user's wall
  @PostMapping("/{id}/wall")
  public String postUserWall(@PathVariable int id, @RequestBody Wall wall) {
    return "User id = " + id + " -> post wall";
  }

  //user Search
  @GetMapping("/search")
  public String getUserSearch(String firstName, String lastName,
      int ageFrom, int ageTo,
      Integer countryId, Integer cityId,
      int offset, int itemPerPage) {

    return "user Search";
  }

  //block user by id
  @PutMapping("/block/{id}")
  public Response<ResponseMessage>  blockUser(@PathVariable int id) {
    ResponseMessage responseMessage = new ResponseMessage();
    return new Response<>(responseMessage);
  }

  //unblock user by id
  @DeleteMapping("/block/{id}")
  public Response<ResponseMessage>  unblockUser(@PathVariable int id) {
    ResponseMessage responseMessage = new ResponseMessage();
    return new Response<>(responseMessage);
  }
}
