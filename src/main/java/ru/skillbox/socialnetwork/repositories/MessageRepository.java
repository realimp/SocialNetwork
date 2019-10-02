package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
