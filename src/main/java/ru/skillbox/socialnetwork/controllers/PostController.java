package ru.skillbox.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.CommentRequest;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.responses.Comment;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("") //Post search
    public ResponseList<PostResponse> postSearch(@RequestParam(required = false) String text, @RequestParam(required = false) Long dateFrom, @RequestParam(required = false) Long dateTo, @RequestParam(required = false) Integer offset, @RequestParam Integer itemPerPage) {
        return new ResponseList<>(new PostResponse());
    }

    @PostMapping("/") //Post creation
    public Response<PostResponse> postCreate(@RequestParam(required = false) Long publishDate, @RequestBody CreatePostRequest createPostRequest) {
        return new Response<>(new PostResponse());
    }

    @GetMapping("/{id}") //Getting post by ID
    public Response<PostResponse> postGetById(@PathVariable int id) {
        return new Response<PostResponse>("Answer to front GET metod",PostMapper.getPostResponse(postService.getOnePostById(id)));
    }

    @PutMapping("/{id}") //Editing a post by ID
    public Response<PostResponse> postEditById(@PathVariable int id, @RequestParam(required = false) Long publishDate, @RequestBody CreatePostRequest createPostRequest) {
        return new Response<>("Answer to front PUT metod", PostMapper.getPostResponse(postService.postEditing(id, createPostRequest)));
    }

    @DeleteMapping("/{id}") //Delete post by ID
    public Response<PostResponse> postDeleteById(@PathVariable int id) {
        postService.deletePostById(id);
        return new Response<>(new PostResponse());
    }

    @PutMapping("/{id}/recover") //Post recovery by ID
    public Response<PostResponse> postRecoverById(@PathVariable int id) {
        postService.recoveryPostById(id);
        return new Response<>(new PostResponse());
    }

    @GetMapping("/{id}/comments") //Getting post comments
    public ResponseList<List<Comment>> postGetComments(@PathVariable int id, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemPerPage) {
        return postService.getComments(id);
    }

    @PostMapping("/{id}/comments") //Post comment
    public Response<Comment> postComments(@PathVariable int id, @RequestBody CommentRequest commentRequest) { //@RequestBody
        return postService.addPostComment(id, commentRequest);
    }

    @PutMapping("/{id}/comments/{comment_id}") //Editing a post comment
    public Response<PostResponse> postCommentsEdit(@PathVariable int id, @PathVariable int comment_id, @RequestBody CommentRequest commentRequest) {//@RequestBody
        return new Response<>(new PostResponse());
    }

    @DeleteMapping("/{id}/comments/{comment_id}") //Removing a post comment
    public Response<PostResponse> postCommentsDelete(@PathVariable int id, @PathVariable int comment_id) {
        return new Response<>(new PostResponse());
    }

    @PutMapping("/{id}/comments/{comment_id}/recover") //Comment recovery
    public Response<PostResponse> postCommentsRecovery(@PathVariable int id, @PathVariable int comment_id) {
        return new Response<>(new PostResponse());
    }

    @PostMapping("/{id}/report") //Post a complaint
    public Response postCommentComplain(@PathVariable int id) {
        return new Response("ok");
    }

    @PostMapping("/{id}/comments/{comment_id}/report") //File a complaint about post comments
    public Response postComplainToComment(@PathVariable int id, @PathVariable int comment_id) {
        return new Response("ok");
    }
}
