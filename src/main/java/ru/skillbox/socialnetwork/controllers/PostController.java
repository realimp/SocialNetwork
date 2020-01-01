package ru.skillbox.socialnetwork.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.CommentRequest;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseList<PostResponse> postSearch(@RequestParam(required = false) String text, @RequestParam(required = false) Long dateFrom, @RequestParam(required = false) Long dateTo, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemPerPage) {
        int pageOffset = offset != null ? offset : 0;
        int itemsPerPage = itemPerPage != null ? itemPerPage : 20;
        Pageable pageable = PageRequest.of(pageOffset, itemsPerPage);
        return  postService.postSearch(text, pageable);
    }

    @PostMapping("/")
    public Response<PostResponse> postCreate(@RequestParam(required = false) Long publishDate, @RequestBody CreatePostRequest createPostRequest) {
        Date publish = publishDate != null ? new Date(publishDate) : new Date();
        return postService.addPost(publish, createPostRequest);
    }

    @GetMapping("/{id}")
    public Response<PostResponse> postGetById(@PathVariable int id) {
        return postService.getOnePostById(id);
    }

    @PutMapping("/{id}")
    public Response<PostResponse> postEditById(@PathVariable int id, @RequestParam(required = false) Long publishDate, @RequestBody CreatePostRequest createPostRequest) {
        return postService.postEditing(id, createPostRequest);
    }

    @DeleteMapping("/{id}")
    public Response<IdResponse> postDeleteById(@PathVariable int id) {
        return postService.deletePostById(id);
    }

    @PutMapping("/{id}/recover")
    public Response<PostResponse> postRecoverById(@PathVariable int id) {
        return postService.recoveryPostById(id);
    }

    @GetMapping("/{id}/comments")
    public ResponseList<List<Comment>> postGetComments(@PathVariable int id, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemPerPage) {
        return postService.getComments(id);
    }

    @PostMapping("/{id}/comments")
    public Response<Comment> postComments(@PathVariable int id, @RequestBody CommentRequest commentRequest) {
        return postService.createPostComment(id, commentRequest);
    }

    @PutMapping("/{id}/comments/{comment_id}")
    public Response<Comment> postCommentsEdit(@PathVariable int id, @PathVariable int comment_id, @RequestBody CommentRequest commentRequest) {//@RequestBody
        return postService.savePostComment(id, comment_id, commentRequest);
    }

    @DeleteMapping("/{id}/comments/{comment_id}")
    public Response<IdResponse> postCommentsDelete(@PathVariable int id, @PathVariable int comment_id) {
        return postService.deletePostComment(id, comment_id);
    }

    @PutMapping("/{id}/comments/{comment_id}/recover")
    public Response<List<Comment>> postCommentsRecovery(@PathVariable int id, @PathVariable int comment_id) {
        return postService.recoveryPostComment(id, comment_id);
    }

    @PostMapping("/{id}/report")
    public Response<MessageResponse> reportPost(@PathVariable int id) {
        return postService.reportPost(id);
    }

    @PostMapping("/{id}/comments/{comment_id}/report")
    public Response<MessageResponse> reportComment(@PathVariable int id, @PathVariable int comment_id) {
        return postService.reportComment(id, comment_id);
    }
}
