package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.skillbox.socialnetwork.api.responses.profile.UserDto;

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
}
