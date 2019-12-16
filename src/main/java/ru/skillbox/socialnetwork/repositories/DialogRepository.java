package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Dialog;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Integer> {
    List<Dialog> findByOwnerId(int id);

    Page<Dialog> findByOwnerId(int id, Pageable pageable);

    Optional<Dialog> findById(int id);

    List<Dialog> findByOwnerAndRecipients(Person owner, List<Person> recipients);
}
