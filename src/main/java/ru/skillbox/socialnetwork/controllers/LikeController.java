package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.LikeRequest;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.LikeType;
import ru.skillbox.socialnetwork.services.AccountService;
import ru.skillbox.socialnetwork.services.LikeService;

@RestController
@RequestMapping
public class LikeController {

    @Autowired
    private LikeService likeService;
    private AccountService accountService;

    @GetMapping("/liked")
    public Response isLiked(@RequestParam LikeType type, @RequestParam Integer itemId,
                            @RequestParam(required = false) Integer userId) {
        return likeService.isLiked(type, itemId, userId != null ? userId : accountService.getCurrentUser().getId());
    }

    @GetMapping("/likes")
    public Response getLikes(@RequestParam LikeType type, @RequestParam Integer itemId) {
        return likeService.getLikeList(type, itemId);
    }

    @PutMapping("/likes")
    public Response putLike(@RequestBody LikeRequest likeRequest) {
        return likeService.putLike(likeRequest.getType(), likeRequest.getPostId());
    }

    @DeleteMapping("/likes")
    public Response deleteLike(@RequestParam LikeType type, @RequestParam Integer itemId) {
        return likeService.deleteLike(type, itemId);
    }


}
