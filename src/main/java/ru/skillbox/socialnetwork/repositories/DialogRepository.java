package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Dialog;

import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Integer> {

    void clear ();

    void block ();

    void mute ();

    Optional<Dialog> findByMessageText (String requiredText);

    Optional<Dialog> findById (Integer id);
    
}
