package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.repositories.CommentLikeRepository;
import ru.skillbox.socialnetwork.repositories.PostCommentRepository;
import ru.skillbox.socialnetwork.repositories.PostLikeRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LikeService {

    @Autowired
    private CommentLikeRepository clRepository;
    @Autowired
    private PostLikeRepository plRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCommentRepository pcRepository;
    @Autowired
    private AccountService accountService;

    public Response isLiked(LikeType type, Integer itemId, Integer userId) {
        boolean isLiked = false;
        if (type.equals(LikeType.POST)) {
            List<PostLike> postLikes = plRepository.findByPersonIdAndPostId(userId, itemId);
            isLiked = !postLikes.isEmpty();
        }
        if (type.equals(LikeType.COMMENT)) {
            List<CommentLike> commentLikes = clRepository.findByPersonIdAndCommentId(userId, itemId);
            isLiked = !commentLikes.isEmpty();
        }
        Response response = new Response(isLiked);
        return response;
    }

    public Response getLikeList(LikeType type, Integer itemId) {
        if (type.equals(LikeType.POST)) {
            List<PostLike> result = plRepository.findByPostId(itemId);
            Response response = new Response(result);
            return response;
        }
        if (type.equals(LikeType.COMMENT)) {
            List<CommentLike> result = clRepository.findByCommentId(itemId);
            Response response = new Response(result);
            return response;
        }
        return null;
    }

    public Response putLike(LikeType type, Integer itemId) {

        Response response = new Response();

        if (type.equals(LikeType.POST)) {
            PostLike like = new PostLike();
            Optional<Post> optional = postRepository.findById(itemId);
            if (!optional.isPresent())
                return new Response(new Error());
            Post post = optional.get();
            like.setDate(new Date(System.currentTimeMillis()));
            like.setPerson(accountService.getCurrentUser());
            like.setPost(post);
            plRepository.saveAndFlush(like);
            response.setData(true);
        }

        if (type.equals(LikeType.COMMENT)) {
            CommentLike like = new CommentLike();
            Optional<PostComment> optional = pcRepository.findById(itemId);
            if (!optional.isPresent())
                return new Response(new Error());
            PostComment comment = optional.get();
            like.setDate(new Date(System.currentTimeMillis()));
            like.setPerson(accountService.getCurrentUser());
            like.setComment(comment);
            clRepository.saveAndFlush(like);
            response.setData(true);
        }

        response.setTimestamp(System.currentTimeMillis());

        return response;
    }

    public Response deleteLike(LikeType type, Integer itemId){
        Integer userId = accountService.getCurrentUser().getId();
        if (type.equals(LikeType.POST)) {
            List<PostLike> list = plRepository.findByPersonIdAndPostId(userId, itemId);
            if (list.isEmpty()) return new Response(new Error("Post not found"));
            plRepository.delete(list.get(0));
        }
        if (type.equals(LikeType.COMMENT)) {
            List<CommentLike> commentLikes = clRepository.findByPersonIdAndCommentId(userId, itemId);
            if (commentLikes.isEmpty()) return new Response(new Error("Comment not found"));
            clRepository.delete(commentLikes.get(0));
        }

        return new Response(Boolean.TRUE);
        
    }

}
