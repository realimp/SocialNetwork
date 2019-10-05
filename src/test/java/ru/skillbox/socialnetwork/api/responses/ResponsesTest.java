package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.skillbox.socialnetwork.api.responses.profile.UserDto;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class ResponsesTest {

    @Test
    public void responsePersonDtoTest() throws JsonProcessingException {
        Response<PersonDto> personDto = new Response<>(new PersonDto());
        personDto.setTimestamp(0);
        assertEquals("response with PersonDto", new ObjectMapper().writeValueAsString(personDto), "{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null}}");
    }

    @Test
    public void responseMessageDtoTest() throws JsonProcessingException {
        Response<MessageDto> messageDto = new Response<>(new MessageDto());
        messageDto.setTimestamp(0);
        assertEquals("response with MessageDto", new ObjectMapper().writeValueAsString(messageDto), "{\"error\":null,\"timestamp\":0,\"data\":{\"message\":null}}");
    }

    @Test
    public void responseUserDtoTest() throws JsonProcessingException {
        Response<UserDto> userDto = new Response<>(new UserDto());
        userDto.setTimestamp(0);
        assertEquals("response with UserDto", new ObjectMapper().writeValueAsString(userDto), "{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"reg_date\":null,\"birth_date\":null,\"email\":null,\"phone\":null,\"photo\":null,\"about\":null,\"city\":{\"id\":null,\"title\":null},\"country\":{\"id\":null,\"title\":null},\"messages_permission\":null,\"last_online_time\":null,\"is_blocked\":null,\"is_friend\":null}}");
    }

    @Test
    public void responseListPersonsWallPostDto() throws JsonProcessingException {
        List<PersonsWallPostDto> personsWallPostDtoList = new ArrayList<>();
        PersonsWallPostDto personWallPostDto = new PersonsWallPostDto();
        personWallPostDto.setAuthor(new BasicPersonDto());
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        personWallPostDto.setTags(tags);
        List<CommentDto> comments = new ArrayList<>();
        CommentDto comment = new CommentDto();
        comment.setAuthor(new BasicPersonDto());
        List<CommentModelDto> subComments = new ArrayList<>();
        CommentModelDto commentModel = new CommentModelDto();
        commentModel.setAuthor(new BasicPersonDto());
        subComments.add(commentModel);
        comment.setSubComments(subComments);
        comments.add(comment);
        personWallPostDto.setComments(comments);
        personsWallPostDtoList.add(personWallPostDto);
        ResponseList<List<PersonsWallPostDto>> personsWallPostDto = new ResponseList<>(personsWallPostDtoList);
        personsWallPostDto.setTimestamp(0);
        assertEquals("response list with PersonsWallPostDto", new ObjectMapper().writeValueAsString(personsWallPostDto), "{\"error\":null,\"timestamp\":0,\"total\":0,\"offset\":0,\"perPage\":0,\"data\":[{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}],\"type\":null}]}");
    }

    @Test
    public void responsePostDtoTest() throws JsonProcessingException {
        PostDto post = new PostDto();
        post.setAuthor(new BasicPersonDto());
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        post.setTags(tags);
        List<CommentDto> comments = new ArrayList<>();
        CommentDto comment = new CommentDto();
        comment.setAuthor(new BasicPersonDto());
        List<CommentModelDto> subComments = new ArrayList<>();
        CommentModelDto commentModel = new CommentModelDto();
        commentModel.setAuthor(new BasicPersonDto());
        subComments.add(commentModel);
        comment.setSubComments(subComments);
        comments.add(comment);
        post.setComments(comments);
        Response<PostDto> postDto = new Response<>(post);
        postDto.setTimestamp(0);
        assertEquals("response with PostDto", new ObjectMapper().writeValueAsString(postDto), "{\"error\":null,\"timestamp\":0,\"data\":{\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"title\":null,\"post_text\":null,\"is_blocked\":null,\"likes\":null,\"tags\":[\"tag1\"],\"my_like\":null,\"comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null,\"post_id\":null,\"sub_comments\":[{\"parent_id\":null,\"comment_text\":null,\"id\":null,\"time\":null,\"author\":{\"id\":null,\"first_name\":null,\"last_name\":null,\"photo\":null,\"last_online_time\":null},\"is_blocked\":null}]}]}}");
    }
}
