package ru.skillbox.socialnetwork.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class, secure = false)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postRegister() throws Exception{
        this.mockMvc.perform(post("/account/register")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("register")));
    }

    @Test
    public void putPasswordRecovery() throws Exception{
        this.mockMvc.perform(put("/account/password/recovery")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("passwordRecovery")));
    }

    @Test
    public void putpasswordSet() throws Exception{
        this.mockMvc.perform(put("/account/password/set")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("passwordSet")));
    }

    @Test
    public void putEmail() throws Exception{
        this.mockMvc.perform(put("/account/email")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("email")));
    }

    @Test
    public void getNotifications() throws Exception {
        this.mockMvc.perform(get("/account/notifications")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("notifications")));
    }

    @Test
    public void putNotifications() throws Exception {
        this.mockMvc.perform(put("/account/notifications")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("putNotifications")));
    }
}
