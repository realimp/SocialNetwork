package ru.skillbox.socialnetwork.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.api.responses.Feeds;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedsServiceTest {

    @Autowired
    private FeedsService feedsService;

    @WithMockUser(username = "paveldobro92@mail.ru")
    @Test
    public void getFeedsTest() {
        Feeds feed = feedsService.getFeeds(null, 0, 20);
        Assert.assertEquals("First post ever!", feed.getFeeds().get(0).getTitle());
    }
}