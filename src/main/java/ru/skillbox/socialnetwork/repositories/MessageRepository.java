package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findByAuthorId(int id, Pageable sortByDate);

    Page<Message> findByDialogIdAndMessageText(int id, String query, Pageable pageable);

    List<Message> findByDialogIdAndMessageText(int id, String query);
}
