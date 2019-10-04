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
import ru.skillbox.socialnetwork.api.responses.PersonDto;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.MessageDto;
import ru.skillbox.socialnetwork.api.responses.profile.UserDto;
import ru.skillbox.socialnetwork.entities.Person;

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
  public Response<MessageDto>  blockUser(@PathVariable int id) {
    MessageDto messageDto = new MessageDto();
    return new Response<>(messageDto);
  }

  //unblock user by id
  @DeleteMapping("/block/{id}")
  public Response<MessageDto>  unblockUser(@PathVariable int id) {
    MessageDto messageDto = new MessageDto();
    return new Response<>(messageDto);
  }
}
