package ru.skillbox.socialnetwork.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.*;

import java.util.Date;

@RestController
@RequestMapping("/feeds")
public class FeedsController {

  @GetMapping("/") //Post search
  public ResponseList<PostResponse> postSearch(String name, int offset, int itemPerPage) {
    return new ResponseList<>(new PostResponse());
  }

}
