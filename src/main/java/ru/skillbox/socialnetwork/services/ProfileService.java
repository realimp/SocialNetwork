/** @Author Savva */
package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.MessagePermission;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.*;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountService accountService;

    public PersonResponse getPerson() {
        Person person = accountService.getCurrentUser();
        PersonResponse personResponse = convertPersonToPersonResponse(person);
        return personResponse;
    }

    public void editPerson(String firstName, String lastName, Date birthDate, String phone, String photoId,
                           String about, Integer cityId, Integer countryId, String messagesPermission) {
        //TODO: City & Country
        Person person = accountService.getCurrentUser();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(birthDate);
        person.setPhone(phone);
        person.setPhoto(photoId);
        person.setAbout(about);
        person.setCity(new City(1, "Moscow").getTitle());
        person.setCountry(new Country(1,"Russia").getTitle());
        person.setMessagesPermission(messagesPermission);
        personRepository.saveAndFlush(person);
    }

    public void deletePerson() {
        Person person = accountService.getCurrentUser();
        person.setDeleted(true);
        personRepository.saveAndFlush(person);
    }

    public PersonResponse getPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        return convertPersonToPersonResponse(person);
    }

    public List<PostResponse> getWallPostsById(Integer id, Integer offset, Integer itemPerPage) {
        //TODO: PostRepository
        return new ArrayList<>();
    }

    public void addWallPostById(Integer id, Date publishDate) {
        //TODO: PostRepository
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
            personResponseList.add(convertPersonToPersonResponse(person));
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

    public PersonResponse convertPersonToPersonResponse(Person person) {
        //TODO: City & Country
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(person.getId());
        personResponse.setFirstName(person.getFirstName());
        personResponse.setLastName(person.getLastName());
        personResponse.setRegDate(person.getRegDate() != null ? person.getRegDate().getTime() : null);
        personResponse.setBirthDate(person.getBirthDate() != null ? person.getBirthDate().getTime() : null);
        personResponse.seteMail(person.getEMail());
        personResponse.setPhone(person.getPhone());
        personResponse.setPhoto(person.getPhoto());
        personResponse.setAbout(person.getAbout());
        personResponse.setCity(new City(1,"Moscow"));
        personResponse.setCountry(new Country(1, "Russia"));
        personResponse.setMessagesPermission(MessagePermission.valueOf(person.getMessagesPermission() != null ?
                person.getMessagesPermission() : "ALL"));
        personResponse.setLastOnlineTime(person.getLastOnlineTime() != null ? person.getLastOnlineTime().getTime() : null);
        personResponse.setBlocked(person.getBlocked());
        return personResponse;
    }

}
