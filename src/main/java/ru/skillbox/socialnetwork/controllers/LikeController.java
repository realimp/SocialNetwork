package ru.skillbox.socialnetwork.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.LikeType;
import ru.skillbox.socialnetwork.services.AccountService;
import ru.skillbox.socialnetwork.services.LikeService;

@RestController
@RequestMapping("")
public class LikeController {

    LikeService likeService = new LikeService();
    AccountService accountService = new AccountService();

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
    public Response putLike(@RequestParam LikeType type, @RequestParam Integer itemId) {
        return likeService.putLike(type, itemId);
    }

    @DeleteMapping("/likes")
    public Response deleteLike(@RequestParam LikeType type, @RequestParam Integer itemId) {
        return likeService.deleteLike(type, itemId);
    }


}
