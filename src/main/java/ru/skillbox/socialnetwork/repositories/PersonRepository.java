package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
