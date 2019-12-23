package ru.skillbox.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.CommentRequest;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("") //Post search
    public ResponseList<PostResponse> postSearch(@RequestParam(required = false) String text, @RequestParam(required = false) Long dateFrom, @RequestParam(required = false) Long dateTo, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemPerPage) {
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
    public Response<IdResponse> postDeleteById(@PathVariable int id) {
        return postService.deletePostById(id);
    }

    @PutMapping("/{id}/recover") //Post recovery by ID
    public Response<PostResponse> postRecoverById(@PathVariable int id) {
        postService.recoveryPostById(id);
        return new Response<>(new PostResponse());
    }

    @GetMapping("/{id}/comments")
    public ResponseList<List<Comment>> postGetComments(@PathVariable int id, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemPerPage) {
        return postService.getComments(id);
    }

    @PostMapping("/{id}/comments")
    public Response<Comment> postComments(@PathVariable int id, @RequestBody CommentRequest commentRequest) { //@RequestBody
        return postService.savePostComment(id, null, commentRequest);
    }

    @PutMapping("/{id}/comments/{comment_id}")
    public Response<Comment> postCommentsEdit(@PathVariable int id, @PathVariable int comment_id, @RequestBody CommentRequest commentRequest) {//@RequestBody
        return postService.savePostComment(id, comment_id, commentRequest);
    }

    @DeleteMapping("/{id}/comments/{comment_id}")
    public Response<IdResponse> postCommentsDelete(@PathVariable int id, @PathVariable int comment_id) {
        return postService.deletePostComment(id, comment_id);
    }

    @PutMapping("/{id}/comments/{comment_id}/recover") //Comment recovery
    public Response<List<Comment>> postCommentsRecovery(@PathVariable int id, @PathVariable int comment_id) {
        return postService.recoveryPostComment(id, comment_id);
    }

    @PostMapping("/{id}/report")
    public Response<MessageResponse> postCommentComplain(@PathVariable int id) {
        // TO-DO: implement method
        return new Response<>(new MessageResponse("ok"));
    }

    @PostMapping("/{id}/comments/{comment_id}/report")
    public Response<MessageResponse> postComplainToComment(@PathVariable int id, @PathVariable int comment_id) {
        // TO-DO: implement method
        return new Response<>(new MessageResponse("ok"));
    }
}
