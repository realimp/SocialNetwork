/** @Author Savva */
package ru.skillbox.socialnetwork.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;
import ru.skillbox.socialnetwork.api.requests.EditPerson;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.mappers.PersonMapper;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.*;

@Service
@Transactional
public class ProfileService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountService accountService;

    public PersonResponse getPerson() {
        Person person = accountService.getCurrentUser();
        if (person != null) {
            logger.info("current user is obtained: {}", person.getId());
        } else {
            logger.warn("could not obtain current user");
        }
        return PersonMapper.getMapping(person);
    }

    public PersonResponse editPerson(EditPerson editPerson) {
        Person person = new Person();
        person.setFirstName(editPerson.getFirstName());
        person.setLastName(editPerson.getLastName());
        person.setBirthDate(editPerson.getBirthDate());
        person.setPhone(editPerson.getPhone());
        person.setPhoto(editPerson.getPhotoId());
        person.setAbout(editPerson.getAbout());
        person.setCity(new City(editPerson.getCityId(), "Moscow").getTitle());
        person.setCountry(new Country(editPerson.getCountryId(),"Russia").getTitle());
        person.setMessagesPermission(editPerson.getMessagesPermission());
        personRepository.saveAndFlush(person);

        return PersonMapper.getMapping(person);
    }

    public PersonResponse deletePerson() {
        Person person = accountService.getCurrentUser();
        if (person != null) {
            logger.info("current user is obtained: {}", person.getId());
        } else {
            logger.warn("could not obtain current user");
        }
        person.setDeleted(true);
        personRepository.saveAndFlush(person);

        return PersonMapper.getMapping(person);
    }

    public PersonResponse getPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        return PersonMapper.getMapping(person);
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
            personResponseList.add(PersonMapper.getMapping(person));
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
