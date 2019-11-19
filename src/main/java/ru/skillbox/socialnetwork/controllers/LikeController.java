package ru.skillbox.socialnetwork.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.LikeType;
import ru.skillbox.socialnetwork.services.LikeService;

@RestController
@RequestMapping("")
public class LikeController {

    LikeService likeService = new LikeService();

    @GetMapping("/liked")
    public Response isLiked (@RequestParam LikeType type, @RequestParam Integer itemId,
                             @RequestParam(required = false) Integer userId) {
        return likeService.isLiked(type, itemId);
    }

    @GetMapping("/likes")
    public Response getLikes(@RequestParam LikeType type, @RequestParam Integer itemId){
        return  null;
    }

    @PutMapping("/likes")
    public Response putLike(@RequestParam LikeType type, @RequestParam Integer itemId){
        return null;
    }

    @DeleteMapping("/likes")
    public Response deleteLike(){
        return null;
    }



}
