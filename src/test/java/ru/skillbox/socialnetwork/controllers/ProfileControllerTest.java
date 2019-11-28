package ru.skillbox.socialnetwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.services.PostService;
import ru.skillbox.socialnetwork.services.ProfileService;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProfileController.class, secure = false)
public class ProfileControllerTest {

    @MockBean
    private ProfileService profileService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMeTest() throws Exception {
        this.mockMvc.perform(get("/users/me")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void putMeTest() throws Exception {
        this.mockMvc.perform(put("/users/me").contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(new PersonResponse())))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void deleteMeTest() throws Exception {
        this.mockMvc.perform(delete("/users/me")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUserTest() throws Exception {
        this.mockMvc.perform(get("/users/{id}", 23)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUserWallTest() throws Exception {
        this.mockMvc.perform(get("/users/{id}/wall", 23)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void postUserWallTest() throws Exception {
        this.mockMvc.perform(post("/users/{id}/wall", 23).contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(new PersonsWallPost())))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUserSearchTest() throws Exception {
        this.mockMvc.perform(get("/users/search", "firstName", "lastName", 25, 27,56, 65,1,24))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void blockUserTest() throws Exception {
        this.mockMvc.perform(put("/users/block/{id}", 23)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void unblockUserTest() throws Exception {
        this.mockMvc.perform(delete("/users/block/{id}", 23)).andDo(print()).andExpect(status().isOk());
    }
}
