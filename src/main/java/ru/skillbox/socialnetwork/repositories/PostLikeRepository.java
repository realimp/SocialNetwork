package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.PostLike;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

    @Query("SELECT l FROM PostLike l JOIN l.person JOIN l.post WHERE l.person=:person_id AND l.post=:post_id")
    List<PostLike> findByPersonIdAndPostId(@Param("person_id") Integer personId, @Param("post_id") Integer postId);

    @Query("SELECT l FROM PostLike l JOIN l.post WHERE l.post=:post_id")
    List<PostLike> findByPostId(@Param("post_id") Integer postId);
}