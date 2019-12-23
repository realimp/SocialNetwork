package ru.skillbox.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.TagRequest;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.services.TagService;


@RestController
@RequestMapping("/tags")
public class TagController {

  @Autowired
  private TagService tagService;

  @GetMapping
  public ResponseList<List<TagResponse>> getTags(@RequestParam(required = false) String tag, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemPerPage) {
    int pageOffset = offset != null ? offset : 0;
    int itemsPerPage = itemPerPage != null ? itemPerPage : 20;
    Pageable resultsPage = PageRequest.of(pageOffset, itemsPerPage);
    return tagService.getTags(tag, resultsPage);
  }

  @PostMapping
  public Response<TagResponse> postTag(@RequestBody TagRequest tag) {
    return tagService.createTag(tag.getTag().trim());
  }

  @DeleteMapping
  public Response<MessageResponse> tagDelete(@RequestParam int id) {
    return tagService.deleteTag(id);
  }
}
