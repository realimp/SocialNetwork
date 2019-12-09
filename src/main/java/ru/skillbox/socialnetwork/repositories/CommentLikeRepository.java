package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.CommentLike;

import java.util.List;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {

    @Query("SELECT l FROM CommentLike l WHERE l.person.id=:person_id AND l.comment.id=:comment_id")
    List<CommentLike> findByPersonIdAndCommentId(@Param("person_id") Integer personId, @Param("comment_id") Integer commentId);

    @Query("SELECT l FROM CommentLike l WHERE l.comment.id=:comment_id")
    List<CommentLike> findByCommentId(@Param("comment_id") Integer commentId);
}
