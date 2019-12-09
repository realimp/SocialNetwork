package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.City;
import ru.skillbox.socialnetwork.entities.Language;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City,Integer> {
    @Query("SELECT n FROM City n WHERE n.country.id = :id AND n.title LIKE %:title%")
    Page<City> findByCountryIdAndTitle(@Param("id") Integer countryId,
                                       @Param("title") String searchString,
                                       Pageable pageable);
}
