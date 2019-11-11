package ru.skillbox.socialnetwork.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    private Post post;

    @Autowired
    PostService postService;

    @Test
    public void deleteAndRecoveryPostByIdTest()
    {
        postService.deletePostById(1);
        post = postService.getOnePostById(1);
        Assert.assertTrue(post.isDeleted());
        postService.recoveryPostById(1);
        post = postService.getOnePostById(1);
        Assert.assertFalse(post.isDeleted());
    }
}
