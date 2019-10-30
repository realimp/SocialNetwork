package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.Date;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByEMail(String email);

    Person findByFirstNameAndLastName(String firstName, String lastName);

    Page<Person> findByFirstNameAndLastNameAndBirthDateBetween(String firstName, String lastName, Date birthDateFrom, Date birthDateTo, Pageable pageable);

    Page<Person> findByFirstNameAndLastNameAndCountry(String firstName, String lastName, String country, Pageable pageable);

    Page<Person> findByFirstNameAndLastNameAndCountryAndCity(String firstName, String lastName, String country, String city, Pageable pageable);

    Page<Person> findByFirstNameAndLastNameAndCountryAndBirthDateBetween(String firstName, String lastName, String country, Date birthDateFrom, Date birthDateTo, Pageable pageable);

    Page<Person> findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween(String firstName, String lastName, String country, String city, Date birthDateFrom, Date birthDateTo, Pageable pageable);
}
