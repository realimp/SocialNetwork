/** @Author Savva */
package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.*;

@Service
public class ProfileService {

    private Person person;

    @Autowired
    private PersonRepository personRepository;

    public ProfileService(Person person) {
        this.person = person;
    }


    public Person getPerson() {
        return person;
    }

    public void editPerson(String first_name, String last_name, Date birth_date, String phone, String photo_id,
                           String about, String town, String country, String messages_permission) {
        //TODO: добавить setPhoto.  photo_id ??
        person.setFirstName(first_name);
        person.setLastName(last_name);
        person.setBirthDate(birth_date);
        person.setPhone(phone);
        //person.setPhoto(photo_id);
        person.setAbout(about);
        person.setCity(town);
        person.setCountry(country);
        person.setMessagesPermission(messages_permission);
    }

    public void deletePerson() {
        person.setDeleted(true);
    }

    public Optional<Person> getPersonById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        return person;
    }

    public List<Post> getWallPostsById(Integer id, Integer offset, Integer itemPerPage) {
        //TODO: Сделать после появления PostRepository
        return new ArrayList<>();
    }

    public void addWallPostById(Integer id, Date publish_date) {
        //TODO: Сделать после появления PostRepository
    }

    public Page<Person> searchPerson(String first_name, String last_name, Integer age_from, Integer age_to,
                                          String country, String city, Integer offset, Integer itemPerPage) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -age_to);
        Date birthDateFrom = calendar.getTime();
        calendar.add(Calendar.YEAR, age_to - age_from);
        Date birthDateTo = calendar.getTime();

        Pageable firstPageWithTenElements = PageRequest.of(0, 10);

        Page<Person> personList = personRepository.findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween(
                first_name, last_name, country, city, birthDateFrom, birthDateTo, firstPageWithTenElements);
        return  personList;
    }

    public void blockPersonById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        person.get().setBlocked(true);
    }

    public void unblockPersonById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        person.get().setBlocked(false);
    }

}
