package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.PostComment;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    @Query("SELECT p FROM PostComment p WHERE p.post.id=:id and p.parentComment = null and p.isDeleted = false")
    List<PostComment> findByPostId(@Param("id") int id);

    @Query("SELECT p FROM PostComment p WHERE p.post.id=:id and p.parentComment.id = :parent_id and p.isDeleted = false")
    List<PostComment> findByPostIdByParentId(@Param("id") int id, @Param("parent_id") int parentId);

}
