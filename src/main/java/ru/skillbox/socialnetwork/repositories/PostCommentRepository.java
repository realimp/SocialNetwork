package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.PostComment;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
}
