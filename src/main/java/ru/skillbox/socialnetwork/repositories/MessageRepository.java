package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Page<Message> findByDialogIdAndMessageText(int id, String query, Pageable pageable);

    Page<Message> findByDialogId(Integer id, Pageable sortByDate);
}
