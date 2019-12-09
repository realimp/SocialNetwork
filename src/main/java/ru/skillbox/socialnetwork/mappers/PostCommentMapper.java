package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.responses.Comment;
import ru.skillbox.socialnetwork.entities.PostComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostCommentMapper {
    private PostCommentMapper(){}

    public static Comment getComment(PostComment postComment) {
        if (postComment == null) return null;
        Comment comment = new Comment();
        comment.setAuthor(PersonToBasicPersonMapper.getBasicPerson(postComment.getAuthor()));
        comment.setBlocked(postComment.isBlocked());
        comment.setCommentText(postComment.getCommentText());
        comment.setId(postComment.getId());
        if (postComment.getParentComment() != null)
            comment.setParentId(postComment.getParentComment().getId());
        comment.setPostId(postComment.getPost().getId().toString());
        if (postComment.getDate() != null)
            comment.setTime(postComment.getDate().getTime());
        return comment;
    }

    private static List<Comment> getComments(List<PostComment> postComments) {
        List<Comment> comments = new ArrayList<>();
        postComments.forEach(c -> comments.add(PostCommentMapper.getComment(c)));
        return comments;
    }

    public static List<Comment> getRootComments(List<PostComment> postComments, Map<Integer, List<PostComment>> childComments) {
        List<Comment> comments = PostCommentMapper.getComments(postComments);
        comments.forEach(c -> c.setSubComments(PostCommentMapper.getComments(childComments.get(c.getId()))));
        return comments;
    }
}
