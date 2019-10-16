/** @Author Savva */
package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.MessagePermission;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.*;

@Service
public class ProfileService {

    @Autowired
    private PersonRepository personRepository;

    public PersonResponse getPerson() {
        //TODO: Get current user w/o id (Spring Security)
        PersonResponse person = new PersonResponse();
        return person;
    }

    public void editPerson(String firstName, String lastName, Date birthDate, String phone, String photoId,
                           String about, Integer cityId, Integer countryId, String messagesPermission) {
        //TODO: Get current user w/o id  &&  update after adding city & country dictionaries
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(birthDate);
        person.setPhone(phone);
        person.setPhoto(photoId);
        person.setAbout(about);
        person.setCity(new City(cityId, "test Title").getTitle());
        person.setCountry(new Country(countryId,"test Title").getTitle());
        person.setMessagesPermission(messagesPermission);
        personRepository.saveAndFlush(person);
    }

    public void deletePerson() {
        //TODO: Get current user w/o id
        Person person = new Person();
        person.setDeleted(true);
        personRepository.saveAndFlush(person);
    }

    public PersonResponse getPersonById(Integer id) {
        //TODO: update after adding city & country dictionaries
        Person person = personRepository.getOne(id);

        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(person.getId());
        personResponse.setFirstName(person.getFirstName());
        personResponse.setLastName(person.getLastName());
        personResponse.setRegDate(person.getRegDate().getTime());
        personResponse.setBirthDate(person.getBirthDate().getTime());
        personResponse.seteMail(person.getEMail());
        personResponse.setPhone(person.getPhone());
        personResponse.setPhoto(person.getPhoto());
        personResponse.setAbout(person.getAbout());
        personResponse.setCity(new City(1,person.getCity()));
        personResponse.setCountry(new Country(1, person.getCountry()));
        personResponse.setMessagesPermission(MessagePermission.valueOf(person.getMessagesPermission()));
        personResponse.setLastOnlineTime(person.getLastOnlineTime().getTime());
        personResponse.setBlocked(person.getBlocked());
        return personResponse;
    }

    public List<PostResponse> getWallPostsById(Integer id, Integer offset, Integer itemPerPage) {
        //TODO: update after adding PostRepository
        return new ArrayList<>();
    }

    public void addWallPostById(Integer id, Date publishDate) {
        //TODO: update after adding PostRepository
    }

    public List<PersonResponse> searchPerson(String firstName, String lastName, Integer ageFrom, Integer ageTo,
                                          String country, String city, Integer offset, Integer itemPerPage) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -ageTo);
        Date birthDateFrom = calendar.getTime();
        calendar.add(Calendar.YEAR, ageTo - ageFrom);
        Date birthDateTo = calendar.getTime();

        Pageable pageable = PageRequest.of(offset, itemPerPage);

        Page<Person> personPageList = personRepository.findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween(
                firstName, lastName, country, city, birthDateFrom, birthDateTo, pageable);

        List<Person> personList = personPageList.getContent();
        List<PersonResponse> personResponseList = new ArrayList<>();

        for (Person person : personList) {
            PersonResponse personResponse = new PersonResponse();
            personResponse.setId(person.getId());
            personResponse.setFirstName(person.getFirstName());
            personResponse.setLastName(person.getLastName());
            personResponse.setRegDate(person.getRegDate().getTime());
            personResponse.setBirthDate(person.getBirthDate().getTime());
            personResponse.seteMail(person.getEMail());
            personResponse.setPhone(person.getPhone());
            personResponse.setPhoto(person.getPhoto());
            personResponse.setAbout(person.getAbout());
            personResponse.setCity(new City(1,person.getCity()));
            personResponse.setCountry(new Country(1, person.getCountry()));
            personResponse.setMessagesPermission(MessagePermission.valueOf(person.getMessagesPermission()));
            personResponse.setLastOnlineTime(person.getLastOnlineTime().getTime());
            personResponse.setBlocked(person.getBlocked());
            personResponseList.add(personResponse);
        }

        return personResponseList;
    }

    public void blockPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        person.setBlocked(true);
        personRepository.saveAndFlush(person);
    }

    public void unblockPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        person.setBlocked(false);
        personRepository.saveAndFlush(person);
    }

}
