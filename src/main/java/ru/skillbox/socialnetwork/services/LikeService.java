package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.repositories.CommentLikeRepository;
import ru.skillbox.socialnetwork.repositories.PostCommentRepository;
import ru.skillbox.socialnetwork.repositories.PostLikeRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import javax.transaction.Transactional;
import java.util.*;

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

    public Response isLiked(String type, Integer itemId, Integer userId) {
        LikeType likeType = LikeType.valueOf(type.trim().toUpperCase());
        boolean isLiked = false;
        Integer currentUserId = userId != null ? userId : accountService.getCurrentUser().getId();
        if (likeType.equals(LikeType.POST)) {
            List<PostLike> postLikes = plRepository.findByPersonIdAndPostId(currentUserId, itemId);
            isLiked = !postLikes.isEmpty();
        }
        if (likeType.equals(LikeType.POST)) {
            List<CommentLike> commentLikes = clRepository.findByPersonIdAndCommentId(currentUserId, itemId);
            isLiked = !commentLikes.isEmpty();
        }

        IsLiked responseData = new IsLiked();
        responseData.setLikes(isLiked);
        Response response = new Response(responseData);
        response.setError("");
        return response;
    }

    public Response getLikeList(String type, Integer itemId) {
        LikeUsersList responseData = new LikeUsersList();
        int count = 0;
        if (type.equalsIgnoreCase(LikeType.POST.toString())) {
            List<PostLike> result = plRepository.findByPostId(itemId);
            List<Integer> users = new ArrayList<>();
            for (PostLike like : result) {
                count++;
                users.add(like.getPerson().getId());
            }
            responseData.setUsers(users.toArray(new Integer[users.size()]));
        }

        if (type.equalsIgnoreCase(LikeType.COMMENT.toString())) {
            List<CommentLike> result = clRepository.findByCommentId(itemId);
            List<Integer> users = new ArrayList<>();
            for (CommentLike like : result) {
                count++;
                users.add(like.getPerson().getId());
            }
            responseData.setUsers(users.toArray(new Integer[users.size()]));
        }

        responseData.setLikes(count);
        Response response = new Response(responseData);
        response.setError("");
        return response;
    }

    public Response putLike(String type, Integer itemId) {
        LikeType likeType = LikeType.valueOf(type.trim().toUpperCase());

        if (likeType.equals(LikeType.POST)) {
            PostLike like = new PostLike();
            Post post = postRepository.findById(itemId).get();
            like.setDate(new Date(System.currentTimeMillis()));
            like.setPerson(accountService.getCurrentUser());
            like.setPost(post);
            plRepository.saveAndFlush(like);
        }

        if (likeType.equals(LikeType.COMMENT)) {
            CommentLike like = new CommentLike();
            PostComment comment = pcRepository.findById(itemId).get();
            like.setDate(new Date(System.currentTimeMillis()));
            like.setPerson(accountService.getCurrentUser());
            like.setComment(comment);
            clRepository.saveAndFlush(like);
        }
        return getLikeList(type, itemId);
    }

    public Response deleteLike(String type, Integer itemId){
        LikeType likeType = LikeType.valueOf(type.trim().toUpperCase());
        LikesCount count = new LikesCount();
        Integer userId = accountService.getCurrentUser().getId();

        if (likeType.equals(LikeType.POST)) {
            List<PostLike> list = plRepository.findByPersonIdAndPostId(userId, itemId);
            plRepository.delete(list.get(0));
            count.setLikes(plRepository.findByPersonIdAndPostId(userId, itemId).size());
        }

        if (likeType.equals(LikeType.COMMENT)) {
            List<CommentLike> list = clRepository.findByPersonIdAndCommentId(userId, itemId);
            clRepository.delete(list.get(0));
            count.setLikes(clRepository.findByPersonIdAndCommentId(userId, itemId).size());
        }

        Response response = new Response(count);
        response.setError("");
        return response;
    }

}
