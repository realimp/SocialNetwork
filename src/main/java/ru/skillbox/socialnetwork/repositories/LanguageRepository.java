package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Language;

@Repository
public interface LanguageRepository extends PagingAndSortingRepository<Language, Integer> {
    @Query("SELECT n FROM Language n WHERE n.title LIKE %:title%")
    Page<Language> findByTitle(@Param("title")String searchString, Pageable pageable);
}
