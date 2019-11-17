package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.responses.Feeds;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.services.FeedsService;
import java.util.List;

@RestController
@RequestMapping("/feeds")
public class FeedsController {

    @Autowired
    FeedsService feedsService;
    private final Integer itemPerPageDefault = 20;
    private final Integer offsetDefault = 0;
    private final String nameDefault = "";

    @GetMapping("")
    public ResponseList<List<PostResponse>> postSearch(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "offset", required = false) Integer offset,
                                                       @RequestParam(value = "itemPerPage", required = false) Integer itemPerPage) {
        if (offset == null) {
          offset = offsetDefault;
        }
        if (name == null) {
          name = nameDefault;
        }
        if (itemPerPage == null) {
          itemPerPage = itemPerPageDefault;
        }
        Feeds feeds = feedsService.getFeeds(name,offset,itemPerPage);
        ResponseList<List<PostResponse>> responseList = new ResponseList<>(feeds.getFeeds());
        responseList.setOffset(offset);
        responseList.setPerPage(itemPerPage);
        responseList.setTimestamp(System.currentTimeMillis());
        responseList.setTotal(feeds.getFeeds().size());
        return responseList;
    }

}
