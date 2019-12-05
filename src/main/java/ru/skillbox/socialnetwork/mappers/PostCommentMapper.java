package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.responses.Comment;
import ru.skillbox.socialnetwork.entities.PostComment;

public class PostCommentMapper {
    private PostCommentMapper(){}

    public static Comment getComment(PostComment postComment) {
        if (postComment == null) return null;
        Comment comment = new Comment();
        comment.setAuthor(PersonToBasicPersonMapper.getMapping(postComment.getAuthor()));
        comment.setBlocked(postComment.isBlocked());
        comment.setCommentText(postComment.getCommentText());
        comment.setId(postComment.getId());
        comment.setParentId(postComment.getParentComment().getId());
        comment.setPostId(postComment.getPost().getId().toString());
        comment.setTime(postComment.getDate().getTime());
        return comment;
    }
}
