package ru.skillbox.socialnetwork.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.socialnetwork.services.PostService;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostController.class, secure = false)
public class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postSearchTest() throws Exception {
        this.mockMvc.perform(get("/post/", "text", new Date(), new Date(), 1, 20)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Post search")));
    }

    @Test
    public void postCreateTest() throws Exception {
        this.mockMvc.perform(post("/post/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Post creation")));
    }

    @Test
    public void postGetByIdTest() throws Exception {
        this.mockMvc.perform(get("/post/{id}", 23)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Getting post by ID")));
    }

    @Test
    public void postEditByIdTest() throws Exception {
        this.mockMvc.perform(put("/post/{id}", 23, new Date())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Editing a post by ID")));
    }

    @Test
    public void postDeleteByIdTest() throws Exception {
        this.mockMvc.perform(delete("/post/{id}", 23)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Delete post by ID")));
    }

    @Test
    public void postRecoverByIdTest() throws Exception {
        this.mockMvc.perform(put("/post/{id}/recover", 23)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Post recovery by ID")));
    }

    @Test
    public void postGetCommentsTest() throws Exception {
        this.mockMvc.perform(get("/post/{id}/comments", 23, 0, 20)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Getting post comments")));
    }

    @Test
    public void postCommentsTest() throws Exception {
        this.mockMvc.perform(post("/post/{id}/comments", 23)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Post comment")));
    }

    @Test
    public void postCommentsEditTest() throws Exception {
        this.mockMvc.perform(put("/post/{id}/comments/{comment_id}", 23, 24)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Editing a post comment")));
    }

    @Test
    public void postCommentsDeleteTest() throws Exception {
        this.mockMvc.perform(delete("/post/{id}/comments/{comment_id}", 23, 24)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Removing a post comment")));
    }

    @Test
    public void postCommentsRecoveryTest() throws Exception {
        this.mockMvc.perform(put("/post/{id}/comments/{comment_id}/recover", 23, 24)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Comment recovery")));
    }

    @Test
    public void postCommentComplainTest() throws Exception {
        this.mockMvc.perform(post("/post/{id}/report", 23, 24)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Comment recovery")));
    }

    @Test
    public void postComplainToCommentTest() throws Exception {
        this.mockMvc.perform(post("/post/{id}/comments/{comment_id}/report", 23, 24)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("File a complaint about post comments")));
    }
}
