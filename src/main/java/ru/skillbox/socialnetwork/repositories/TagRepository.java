package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

    Optional<Tag> findById(int id);

    List<Tag> findByText(String text); //Поиск всех тегов содержащих текст
}
