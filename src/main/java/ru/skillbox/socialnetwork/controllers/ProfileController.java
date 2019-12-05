package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.requests.EditPerson;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.Tag;
import ru.skillbox.socialnetwork.services.PostService;
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

    @GetMapping("/{id}")
    public Response<PersonResponse> getUser(@PathVariable Integer id) {
        PersonResponse personResponse = profileService.getPersonById(id);
        return new Response<>(personResponse);
    }

    //getting posts on the user's wall
    @GetMapping("/{id}/wall")
    public ResponseList<List<PersonsWallPost>> getUserWall(@PathVariable Integer id, @RequestParam(required = false) Integer offset,  @RequestParam(required = false) Integer itemPerPage) {
        int pageOffset = 0, itemsPerPage = 20;
        if (offset != null) {
            pageOffset = offset;
        }
        if (itemPerPage != null) {
            itemsPerPage = itemPerPage;
        }
        List<PersonsWallPost> personsWallPostList = profileService.getWallPostsById(id, pageOffset, itemsPerPage);
        ResponseList<List<PersonsWallPost>> responseList = new ResponseList<>(personsWallPostList, personsWallPostList.size());
        responseList.setError("");
        return  responseList;
    }

    //adding a post to a user's wall
    @PostMapping("/{id}/wall")
    public Response<PostResponse> postUserWall(@PathVariable Integer id, @RequestParam(name = "publish_date", required = false) Long publishDate, @RequestBody CreatePostRequest createPostRequest) {
        Date date = new Date();
        List<Tag> tagsList = new ArrayList<>();
        if (publishDate != null) {
            date = new Date(publishDate);
        }
        if (createPostRequest.getTags().length > 0) {
            for (String tagString : createPostRequest.getTags()) {
                Tag tag = new Tag();
                tag.setText(tagString);
                tagsList.add(tag);
            }
        }
        PostResponse postResponse = profileService.addWallPostById(id, date, createPostRequest.getTitle(), createPostRequest.getPostText(), tagsList);
        return new Response<>(postResponse);
    }

    //user Search
    @GetMapping("/search")
    public ResponseList<List<PersonResponse>> getUserSearch(@RequestParam(required = false) String first_name, @RequestParam(required = false) String lastName,
                                                            @RequestParam(required = false) Integer ageFrom, @RequestParam(required = false) Integer ageTo,
                                                            @RequestParam(required = false) String country, @RequestParam(required = false) String city,
                                                            @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemPerPage) {
        String firstNameQuery = first_name != null ? first_name : "";
        String lastNameQuery = lastName != null ? lastName : "";
        int ageSearchFrom = ageFrom != null ? ageFrom : 0;
        int ageSearchTo = ageTo != null ? ageTo : Integer.MAX_VALUE;
        String countryQuery = country != null ? country : "";
        String cityQuery = city != null ? city : "";
        int pageOffset = offset != null ? offset : 0;
        int itemsPerPage = itemPerPage != null ? itemPerPage : 20;

        List<PersonResponse> personResponseList = profileService.searchPerson(firstNameQuery, lastNameQuery, ageSearchFrom, ageSearchTo, countryQuery, cityQuery, pageOffset, itemsPerPage);
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
        MessageResponse messageResponse = profileService.unblockPersonById(id);
        return new Response<>(messageResponse);
    }
}
