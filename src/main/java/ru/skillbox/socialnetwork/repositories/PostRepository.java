package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;

import java.util.Date;
import java.util.List;

/*
    @Query("SELECT l FROM CommentLike l JOIN l.comment WHERE l.comment=:comment_id")
    List<CommentLike> findByCommentId(@Param("comment_id") Integer commentId);

 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    //List<Post> findByTitleAndText(String searchString);

//    @Query("SELECT * FROM post WHERE time >=:time")
//    List<Post> findByDateFrom(@Param("time") Date fromDate);
//
//    @Query("SELECT * FROM post WHERE time <=:time")
//    List<Post> findByDateTo(@Param("time") Date fromDate);

    Page<Post> findByAuthor(Person author, Pageable pageable);

}
