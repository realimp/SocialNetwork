package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
