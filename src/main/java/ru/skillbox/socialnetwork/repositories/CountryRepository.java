package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Country;
import ru.skillbox.socialnetwork.entities.Language;

@Repository
public interface CountryRepository extends PagingAndSortingRepository<Country, Integer> {
    @Query("SELECT n FROM Country n WHERE n.title LIKE %:title%")
    Page<Country> findByTitle(@Param("title")String searchString, Pageable pageable);
}
