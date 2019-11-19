package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.CommentLike;
import ru.skillbox.socialnetwork.entities.LikeType;
import ru.skillbox.socialnetwork.entities.PostLike;
import ru.skillbox.socialnetwork.repositories.CommentLikeRepository;
import ru.skillbox.socialnetwork.repositories.PostLikeRepository;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    CommentLikeRepository clRepository;

    @Autowired
    PostLikeRepository plRepository;

    public Response isLiked(LikeType type, Integer itemId){
        boolean isLiked = false;
        if (type.equals(LikeType.POST)){
            Optional<PostLike> postLike = plRepository.findById(itemId);
            isLiked = postLike.isPresent();
        }
        if (type.equals(LikeType.COMMENT)){
            Optional<CommentLike> commentLike = clRepository.findById(itemId);
            isLiked = commentLike.isPresent();
        }
        Response response = new Response(isLiked);
        return response;
    }

}
