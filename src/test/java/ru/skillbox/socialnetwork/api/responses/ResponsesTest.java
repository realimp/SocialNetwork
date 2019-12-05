package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;
import java.util.List;

public class ResponsesTest {

    @Test
    public void responsePersonTest() throws JsonProcessingException, JSONException {
        PersonResponse person = new PersonResponse();
        person.setCity(new CityResponse().getTitle());
        person.setCountry(new CountryResponse().getTitle());
        Response<PersonResponse> response = new Response<>(person);
        response.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":null,\"country\":null,\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null}}",
                new ObjectMapper().writeValueAsString(response), true);

    }

    @Test
    public void responseMessageTest() throws JsonProcessingException, JSONException {
        Response<MessageResponse> message = new Response<>(new MessageResponse());
        message.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":{\"message\":null}}",
                new ObjectMapper().writeValueAsString(message), true);

    }

    @Test
    public void responseListPersonsWallPost() throws JsonProcessingException, JSONException {
        List<PersonsWallPost> personsWallPostList = new ArrayList<>();
        PersonsWallPost personWallPost = new PersonsWallPost();
        personWallPost.setAuthor(new PersonResponse());
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        personWallPost.setTags(tags);
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setAuthor(new BasicPerson());
        List<Comment> subComments = new ArrayList<>();
        Comment commentModel = new Comment();
        commentModel.setAuthor(new BasicPerson());
        subComments.add(commentModel);
        comment.setSubComments(subComments);
        comments.add(comment);
        personWallPost.setComments(comments);
        personsWallPostList.add(personWallPost);
        ResponseList<List<PersonsWallPost>> personsWallPost = new ResponseList<>(personsWallPostList);
        personsWallPost.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"total\":0,\"offset\":0,\"perPage\":0,\"data\":[{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}],\"type\":null}]}",
                new ObjectMapper().writeValueAsString(personsWallPost), false);

    }

    @Test
    public void responsePostTest() throws JsonProcessingException, JSONException {
        PostResponse postResponse = new PostResponse();
        postResponse.setAuthor(new BasicPerson());
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        postResponse.setTags(tags);
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setAuthor(new BasicPerson());
        List<Comment> subComments = new ArrayList<>();
        Comment commentModel = new Comment();
        commentModel.setAuthor(new BasicPerson());
        subComments.add(commentModel);
        comment.setSubComments(subComments);
        comments.add(comment);
        postResponse.setComments(comments);
        Response<PostResponse> post = new Response<>(postResponse);
        post.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}]}}",
                new ObjectMapper().writeValueAsString(post), false);

    }

    @Test
    public void responseListPersonsTest() throws JsonProcessingException, JSONException {
        List<PersonResponse> personList = new ArrayList<>();
        PersonResponse person = new PersonResponse();
        person.setCity(new CityResponse().getTitle());
        person.setCountry(new CountryResponse().getTitle());
        personList.add(person);
        ResponseList<List<PersonResponse>> persons = new ResponseList<>(personList);
        persons.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"total\":0,\"offset\":0,\"perPage\":0,\"data\":[{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":null,\"country\":null,\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null}]}",
                new ObjectMapper().writeValueAsString(persons), true);

    }
}
