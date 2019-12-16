package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.Tag;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    //List<Post> findByTitleAndText(String searchString);

//    @Query("SELECT * FROM post WHERE time >=:time")
//    List<Post> findByDateFrom(@Param("time") Date fromDate);
//
//    @Query("SELECT * FROM post WHERE time <=:time")
//    List<Post> findByDateTo(@Param("time") Date fromDate);
    @Query(nativeQuery = true,
        value = "SELECT * FROM post p WHERE p.author_id=:authorId AND p.is_deleted=0 ORDER BY time")
    Page<Post> findByAuthorId(Integer authorId, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM post p WHERE p.author_id IN :ids AND p.is_deleted=0 ORDER BY time")
    Page<Post> findByManyAuthors(@Param("ids") List<Integer> ids, Pageable pageable);

    Page<Post> findByTags(Tag tag, Pageable resultsPage);
}
