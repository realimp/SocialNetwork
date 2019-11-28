package ru.skillbox.socialnetwork.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.api.responses.Feeds;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedsServiceTest {

    @Autowired
    FeedsService feedsService;

    @Test
    public void getFeedsTest() {
        Feeds feeds = feedsService.getFeeds("",0,10);
        feeds.getFeeds().forEach(f->{
            System.out.println(f.toString());
        });
        Assert.assertNotNull(feeds);
    }

}
