//Автор:
//Имя: Дмитрий Хрипков
//Псевдоним: X64
//Почта: HDV_1990@mail.ru
package ru.skillbox.socialnetwork.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.api.responses.TagResponse;

@RestController
@RequestMapping("/Tag")
public class TagController {

  @GetMapping("/") //Tag search
  public ResponseList<TagResponse> tagSearch(String text) {
    return new ResponseList<>(new TagResponse());
  }

}
