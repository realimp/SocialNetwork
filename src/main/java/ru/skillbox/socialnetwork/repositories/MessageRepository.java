package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

}
