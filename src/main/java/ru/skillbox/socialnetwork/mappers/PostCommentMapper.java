package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.responses.Comment;
import ru.skillbox.socialnetwork.entities.PostComment;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Comment> getComments(List<PostComment> postComments) {
        List<Comment> comments = new ArrayList<>();
        postComments.forEach(c -> comments.add(PostCommentMapper.getComment(c)));
        return comments;
    }
}
