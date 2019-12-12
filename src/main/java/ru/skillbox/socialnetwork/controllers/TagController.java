//Автор:
//Имя: Дмитрий Хрипков
//Псевдоним: X64
//Почта: HDV_1990@mail.ru
package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.repositories.*;
import ru.skillbox.socialnetwork.services.AccountService;
import ru.skillbox.socialnetwork.services.TagService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Tag")
public class TagController {

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  PostRepository postRepository;

  @GetMapping
  public ResponseList getPosts(@RequestParam(required = false) String tag, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemsPerPage) {
    Pageable resultsPage;
    if (offset != null && itemsPerPage != null) {
      resultsPage = PageRequest.of(offset, itemsPerPage);
    } else {
      resultsPage = PageRequest.of(0, 20);
    }
    Page<Post> results =  postRepository.findByTag(tag, resultsPage);

    ArrayList<PostResponse> postResponses = new ArrayList<>();

    if (results.getTotalElements() > 0) {

      //TODO: implement query search

      for (Post result : results) {
        Pageable sortByDate = PageRequest.of(0, 1, Sort.by("time"));
        Page<Post> lastPostPage = postRepository.findByTag(tag, sortByDate);
        PostResponse postResponse = new PostResponse();
        postResponse.setId(result.getId());
        postResponses.add(postResponse);
        }
      }

    ResponseList response = new ResponseList(postResponses);
    response.setError("");
    response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
    response.setTotal(results.getTotalElements());
    response.setOffset(resultsPage.getOffset());
    response.setPerPage(resultsPage.getPageSize());
    response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());

    return response;
  }

  @PostMapping
  public Response postTag(@RequestParam String text) {
    Tag tag = new Tag(text);
    TagResponse responseData = new TagResponse();

   //Определить ID
    responseData.setId(tag.getId());
    Response response = new Response(responseData);
    response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
    response.setError("");
    return response;
  }

  @DeleteMapping
  public Response<TagResponse> tagDelete(@PathVariable int id) {
    return new Response<>(new TagResponse());

  }
}
