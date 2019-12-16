package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Page<Tag> findByTag(String text, Pageable pageable);

    Tag findByTag(String text);

    Boolean existsByTag(String text);
}
