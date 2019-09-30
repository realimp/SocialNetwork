package ru.skillbox.socialnetwork.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.requests.ProfilPutModel;
import ru.skillbox.socialnetwork.api.requests.WallPostModel;

@RestController
@RequestMapping("/users")
public class ProfileController {

  //mapping for a current user
  @GetMapping("/me")
  public String getMe() {
    return "get Me";
  }

  @PutMapping("/me")
  public String putMe(@RequestBody ProfilPutModel profilPutModel) {
    return "put Me";
  }

  @DeleteMapping("/me")
  public String deleteMe() {
    return "delete Me";
  }

  //get user by id
  @GetMapping("/{id}")
  public String getUser(@PathVariable int id) {
    return "get User id = " + id;
  }

  //getting posts on the user's wall
  @GetMapping("/{id}/wall")
  public String getUserWall(@PathVariable int id) {
    return "User id = " + id + " -> get wall";
  }

  //adding a post to a user's wall
  @PostMapping("/{id}/wall")
  public String postUserWall(@PathVariable int id, @RequestBody WallPostModel wallPostModel) {
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
  public String blockUser(@PathVariable int id) {
    return "block User id = " + id;
  }

  //unblock user by id
  @DeleteMapping("/block/{id}")
  public String unblockUser(@PathVariable int id) {
    return "unblock User id = " + id;
  }
}
