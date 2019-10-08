package ru.skillbox.socialnetwork.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testAddOnePerson() {
        int sizeBeforeAddingPerson = personRepository.findAll().size();
        Person person = new Person();
        person.setFirstName("Oleg");
        person.setLastName("Petrov");
        person.setPhone("9009001010");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        personRepository.save(person);
        assertEquals(sizeBeforeAddingPerson + 1, personRepository.findAll().size());
        personRepository.delete(person);
    }
    @Test
    public void testUpdatePerson() {
        Person person = new Person();
        person.setFirstName("Oleg");
        person.setLastName("Petrov");
        person.setPhone("8881112266");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        personRepository.save(person);
        Person personToUpdate = personRepository.findAll().get(0);
        personToUpdate.setFirstName("TestPersonFirstName");
        personRepository.save(personToUpdate);
        assertEquals("TestPersonFirstName", personRepository.findById(personToUpdate.getId()).get().getFirstName());
        personRepository.delete(personToUpdate);
    }
    @Test
    public void testDeletePerson() {
        int sizeBeforeAddingPerson = personRepository.findAll().size();
        Person person = new Person();
        person.setFirstName("Oleg");
        person.setLastName("Petrov");
        person.setPhone("8881112266");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        personRepository.save(person);
        personRepository.delete(person);
        assertEquals(sizeBeforeAddingPerson, personRepository.findAll().size());
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        Person person = new Person();
        person.setFirstName("TestPersonFirstName");
        person.setLastName("TestPersonLastName");
        person.setPhone("1234567890");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        personRepository.save(person);
        Pageable firstPage = PageRequest.of(0, 20);
        assertEquals(1, personRepository.findByFirstNameAndLastName("TestPersonFirstName", "TestPersonLastName", firstPage).getNumberOfElements());
        personRepository.delete(person);
    }

    @Test
    public void testFindByFirstNameAndLastNameAndBirthDateBetween() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date testPersonBirthDate = dateFormat.parse("07.09.1973");
        Date testSearchFrom = dateFormat.parse("07.09.1970");
        Date testSearchTo = dateFormat.parse("07.09.1975");

        Person person = new Person();
        person.setFirstName("TestPersonFirstName");
        person.setLastName("TestPersonLastName");
        person.setPhone("1234567890");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        person.setBirthDate(testPersonBirthDate);
        personRepository.save(person);
        Pageable firstPage = PageRequest.of(0, 20);
        assertEquals(1, personRepository.findByFirstNameAndLastNameAndBirthDateBetween("TestPersonFirstName", "TestPersonLastName", testSearchFrom, testSearchTo, firstPage).getNumberOfElements());
        personRepository.delete(person);
    }

    @Test
    public void testFindByFirstNameAndLastNameAndCountry() {
        Person person = new Person();
        person.setFirstName("TestPersonFirstName");
        person.setLastName("TestPersonLastName");
        person.setPhone("1234567890");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        person.setCountry("TestCountry");
        personRepository.save(person);
        Pageable firstPage = PageRequest.of(0, 20);
        assertEquals(1, personRepository.findByFirstNameAndLastNameAndCountry("TestPersonFirstName", "TestPersonLastName", "TestCountry", firstPage).getNumberOfElements());
        personRepository.delete(person);
    }

    @Test
    public void testFindByFirstNameAndLastNameAndCountryAndCity() {
        Person person = new Person();
        person.setFirstName("TestPersonFirstName");
        person.setLastName("TestPersonLastName");
        person.setPhone("1234567890");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        person.setCountry("TestCountry");
        person.setCity("TestCity");
        personRepository.save(person);
        Pageable firstPage = PageRequest.of(0, 20);
        assertEquals(1, personRepository.findByFirstNameAndLastNameAndCountryAndCity("TestPersonFirstName", "TestPersonLastName", "TestCountry", "TestCity", firstPage).getNumberOfElements());
        personRepository.delete(person);
    }

    @Test
    public void testFindByFirstNameAndLastNameAndCountryAndBirthDateBetween() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date testPersonBirthDate = dateFormat.parse("07.09.1973");
        Date testSearchFrom = dateFormat.parse("07.09.1970");
        Date testSearchTo = dateFormat.parse("07.09.1975");

        Person person = new Person();
        person.setFirstName("TestPersonFirstName");
        person.setLastName("TestPersonLastName");
        person.setPhone("1234567890");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        person.setCountry("TestCountry");
        person.setBirthDate(testPersonBirthDate);
        personRepository.save(person);
        Pageable firstPage = PageRequest.of(0, 20);
        assertEquals(1, personRepository.findByFirstNameAndLastNameAndCountryAndBirthDateBetween("TestPersonFirstName", "TestPersonLastName", "TestCountry", testSearchFrom, testSearchTo, firstPage).getNumberOfElements());
        personRepository.delete(person);
    }

    @Test
    public void testFindByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date testPersonBirthDate = dateFormat.parse("07.09.1973");
        Date testSearchFrom = dateFormat.parse("07.09.1970");
        Date testSearchTo = dateFormat.parse("07.09.1975");

        Person person = new Person();
        person.setFirstName("TestPersonFirstName");
        person.setLastName("TestPersonLastName");
        person.setPhone("1234567890");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        person.setCountry("TestCountry");
        person.setCity("TestCity");
        person.setBirthDate(testPersonBirthDate);
        personRepository.save(person);
        Pageable firstPage = PageRequest.of(0, 20);
        assertEquals(1, personRepository.findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween("TestPersonFirstName", "TestPersonLastName", "TestCountry", "TestCity", testSearchFrom, testSearchTo, firstPage).getNumberOfElements());
        personRepository.delete(person);
    }
}
