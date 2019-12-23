package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.LikeRequest;
import ru.skillbox.socialnetwork.api.responses.IsLiked;
import ru.skillbox.socialnetwork.api.responses.LikeUsersList;
import ru.skillbox.socialnetwork.api.responses.LikesCount;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.services.LikeService;

@RestController
@RequestMapping
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/liked")
    public Response<IsLiked> isLiked(@RequestParam String type, @RequestParam("item_id") Integer itemId,
                            @RequestParam(required = false, name = "user_id") Integer userId) {
        return likeService.isLiked(type, itemId, userId);
    }

    @GetMapping("/likes")
    public Response<LikeUsersList> getLikes(@RequestParam String type, @RequestParam("item_id") Integer itemId) {
        return likeService.getLikeList(type, itemId);
    }

    @PutMapping("/likes")
    public Response<LikeUsersList> putLike(@RequestBody LikeRequest likeRequest) {
        return likeService.putLike(likeRequest.getType(), likeRequest.getPostId());
    }

    @DeleteMapping("/likes")
    public Response<LikesCount> deleteLike(@RequestParam String type, @RequestParam(name = "item_id") Integer itemId) {
        return likeService.deleteLike(type, itemId);
    }

}
