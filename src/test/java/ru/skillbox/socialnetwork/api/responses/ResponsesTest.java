package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;
import ru.skillbox.socialnetwork.api.responses.profile.User;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class ResponsesTest {

    @Test
    public void responsePersonDtoTest() throws JsonProcessingException {
        Person person = new Person();
        person.setCity(new City());
        person.setCountry(new Country());
        Response<Person> response = new Response<>(person);
        response.setTimestamp(0);
        assertEquals("response with Person", new ObjectMapper().writeValueAsString(response), "{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null}}");
    }

    @Test
    public void responseMessageDtoTest() throws JsonProcessingException {
        Response<Message> messageDto = new Response<>(new Message());
        messageDto.setTimestamp(0);
        assertEquals("response with Message", new ObjectMapper().writeValueAsString(messageDto), "{\"error\":null,\"timestamp\":0,\"data\":{\"message\":null}}");
    }

    @Test
    public void responseUserDtoTest() throws JsonProcessingException {
        User user = new User();
        user.setCity(new City());
        user.setCountry(new Country());
        Response<User> response = new Response<>(user);
        response.setTimestamp(0);
        assertEquals("response with User", new ObjectMapper().writeValueAsString(response), "{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null,\"is_friend\":null}}");
    }

    @Test
    public void responseListPersonsWallPostDto() throws JsonProcessingException {
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
        assertEquals("response list with PersonsWallPost list", new ObjectMapper().writeValueAsString(personsWallPostDto), "{\"error\":null,\"timestamp\":0,\"total\":0,\"offset\":0,\"perPage\":0,\"data\":[{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}],\"type\":null}]}");
    }

    @Test
    public void responsePostDtoTest() throws JsonProcessingException {
        Post post = new Post();
        post.setAuthor(new BasicPerson());
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        post.setTags(tags);
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setAuthor(new BasicPerson());
        List<CommentModel> subComments = new ArrayList<>();
        CommentModel commentModel = new CommentModel();
        commentModel.setAuthor(new BasicPerson());
        subComments.add(commentModel);
        comment.setSubComments(subComments);
        comments.add(comment);
        post.setComments(comments);
        Response<Post> postDto = new Response<>(post);
        postDto.setTimestamp(0);
        assertEquals("response with Post", new ObjectMapper().writeValueAsString(postDto), "{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}]}}");
    }

    @Test
    public void responseListPersonsDtoTest() throws JsonProcessingException {
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setCity(new City());
        person.setCountry(new Country());
        personList.add(person);
        ResponseList<List<Person>> personsDto = new ResponseList<>(personList);
        personsDto.setTimestamp(0);
        assertEquals("response list with Person list", new ObjectMapper().writeValueAsString(personsDto), "{\"error\":null,\"timestamp\":0,\"total\":0,\"offset\":0,\"perPage\":0,\"data\":[{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null}]}");
    }
}
