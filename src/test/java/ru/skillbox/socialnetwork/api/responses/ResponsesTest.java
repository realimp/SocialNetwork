package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;

import java.util.ArrayList;
import java.util.List;

public class ResponsesTest {

    @Test
    public void responsePersonDtoTest() throws JsonProcessingException, JSONException {
        PersonResponse person = new PersonResponse();
        person.setCity(new City());
        person.setCountry(new Country());
        Response<PersonResponse> response = new Response<>(person);
        response.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null}}",
                new ObjectMapper().writeValueAsString(response), true);

    }

    @Test
    public void responseMessageDtoTest() throws JsonProcessingException, JSONException {
        Response<MessageResponse> messageDto = new Response<>(new MessageResponse());
        messageDto.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":{\"message\":null}}",
                new ObjectMapper().writeValueAsString(messageDto), true);

    }

    @Test
    public void responseUserDtoTest() throws JsonProcessingException, JSONException {
        User user = new User();
        user.setCity(new City());
        user.setCountry(new Country());
        Response<User> response = new Response<>(user);
        response.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null,\"is_friend\":null}}",
                new ObjectMapper().writeValueAsString(response), true);

    }

    @Test
    public void responseListPersonsWallPostDto() throws JsonProcessingException, JSONException {
        List<PersonsWallPost> personsWallPostList = new ArrayList<>();
        PersonsWallPost personWallPostDto = new PersonsWallPost();
        personWallPostDto.setAuthor(new BasicPerson());
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        personWallPostDto.setTags(tags);
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setAuthor(new BasicPerson());
        List<CommentModel> subComments = new ArrayList<>();
        CommentModel commentModel = new CommentModel();
        commentModel.setAuthor(new BasicPerson());
        subComments.add(commentModel);
        comment.setSubComments(subComments);
        comments.add(comment);
        personWallPostDto.setComments(comments);
        personsWallPostList.add(personWallPostDto);
        ResponseList<List<PersonsWallPost>> personsWallPostDto = new ResponseList<>(personsWallPostList);
        personsWallPostDto.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"total\":0,\"offset\":0,\"perPage\":0,\"data\":[{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}],\"type\":null}]}",
                new ObjectMapper().writeValueAsString(personsWallPostDto), true);

    }

    @Test
    public void responsePostDtoTest() throws JsonProcessingException, JSONException {
        PostResponse postResponse = new PostResponse();
        postResponse.setAuthor(new BasicPerson());
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        postResponse.setTags(tags);
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setAuthor(new BasicPerson());
        List<CommentModel> subComments = new ArrayList<>();
        CommentModel commentModel = new CommentModel();
        commentModel.setAuthor(new BasicPerson());
        subComments.add(commentModel);
        comment.setSubComments(subComments);
        comments.add(comment);
        postResponse.setComments(comments);
        Response<PostResponse> postDto = new Response<>(postResponse);
        postDto.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}]}}",
                new ObjectMapper().writeValueAsString(postDto), true);

    }

    @Test
    public void responseListPersonsDtoTest() throws JsonProcessingException, JSONException {
        List<PersonResponse> personList = new ArrayList<>();
        PersonResponse person = new PersonResponse();
        person.setCity(new City());
        person.setCountry(new Country());
        personList.add(person);
        ResponseList<List<PersonResponse>> personsDto = new ResponseList<>(personList);
        personsDto.setTimestamp(0);
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"total\":0,\"offset\":0,\"perPage\":0,\"data\":[{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null}]}",
                new ObjectMapper().writeValueAsString(personsDto), true);

    }
}
