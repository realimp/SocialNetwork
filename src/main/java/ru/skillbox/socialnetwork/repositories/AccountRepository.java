package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByEMail(String email);
}
