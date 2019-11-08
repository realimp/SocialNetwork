package ru.skillbox.socialnetwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.services.AccountService;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class, secure = false)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private MessageResponse message;
    private Response response;

    @Before
    public void setUp() {
        message = new MessageResponse();
        message.setMessage("ok");
        long timestamp = 0;
        response = new Response(message);
        response.setTimestamp(timestamp);
    }

        @Test
    public void registerTest() throws Exception{
        this.mockMvc.perform(post("/account/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(response)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void passwordRecoveryTest() throws Exception{
        this.mockMvc.perform(put("/account/password/recovery")
                .contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(response)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void passwordSetTest() throws Exception{
        this.mockMvc.perform(put("/account/password/set")
                .contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(response)))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void emailTest() throws Exception{
        this.mockMvc.perform(put("/account/email")
                .contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(response)))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void notificationsTest() throws Exception {
        this.mockMvc.perform(get("/account/notifications")
                .contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(response)))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void putNotificationsTest() throws Exception {
        this.mockMvc.perform(put("/account/notifications")
                .contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(response)))
                .andDo(print()).andExpect(status().isOk());
    }
}