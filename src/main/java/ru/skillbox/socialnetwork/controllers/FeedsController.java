//Автор:
//Имя: Дмитрий Хрипков
//Псевдоним: X64
//Почта: HDV_1990@mail.ru
package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.services.FeedsService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/feeds")
public class FeedsController {

  @Autowired
  FeedsService feedsService;

  @GetMapping("") //Post search
  public ResponseList<List<PostResponse>> postSearch(String name, Integer offset, Integer itemPerPage) {
    if (offset == null) {
      offset = 0;
    }
    if (name == null) {
      name = "";
    }
    if (itemPerPage == null) {
      itemPerPage = 10;
    }
    Feeds feeds = feedsService.getFeeds(name,offset,itemPerPage);
    feeds.getFeeds().forEach(feed->{
      System.out.println(feed.toString());
    });
    ResponseList<List<PostResponse>> responseList = new ResponseList<>(feeds.getFeeds());
    responseList.setOffset(offset);
    responseList.setPerPage(itemPerPage);
    responseList.setTimestamp(System.currentTimeMillis());
    return responseList;
  }

}
