package ru.skillbox.socialnetwork.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void findByEMailTest() {
        Person person = personRepository.findByEMail("paveldobro92@mail.ru");
        Assert.assertEquals(Integer.valueOf(1), person.getId());
        Assert.assertEquals("paveldobro92@mail.ru", person.getEMail());
        Assert.assertEquals("Pavel", person.getFirstName());
        Assert.assertEquals("Dobromirov", person.getLastName());
    }

    @Test
    public void findByFirstNameTest() {
        Page<Person> page =  personRepository.findByFirstName("testName", PageRequest.of(0, 20));
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByLastNameTest() {
        Page<Person> page = personRepository.findByLastName("testLastName", PageRequest.of(0, 20));
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByFirstNameAndLastNameTest() {
        Page<Person> page = personRepository.findByFirstNameAndLastName("testName", "testLastName", PageRequest.of(0, 20));
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByFirstNameAndLastNameAndBirthDateBetweenTest() throws ParseException {
        Date testSearchFrom = dateFormat.parse("07.09.1990");
        Date testSearchTo = dateFormat.parse("07.09.1993");

        Page<Person> page = personRepository.findByFirstNameAndLastNameAndBirthDateBetween(
                "testName",
                "testLastName",
                testSearchFrom,
                testSearchTo,
                PageRequest.of(0, 20));
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByFirstNameAndLastNameAndCountryTest() {
        Page<Person> page = personRepository.findByFirstNameAndLastNameAndCountry(
                "testName", "testLastName", "Russia", PageRequest.of(0, 20));
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByFirstNameAndLastNameAndCountryAndCityTest() {
        Page<Person> page = personRepository.findByFirstNameAndLastNameAndCountryAndCity(
                "testName", "testLastName", "Russia", "Moscow", PageRequest.of(0, 20));
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByFirstNameAndLastNameAndCountryAndBirthDateBetweenTest() throws ParseException {
        Date testSearchFrom = dateFormat.parse("07.09.1990");
        Date testSearchTo = dateFormat.parse("07.09.1993");

        Page<Person> page = personRepository.findByFirstNameAndLastNameAndCountryAndBirthDateBetween(
                "testName",
                "testLastName",
                "Russia",
                testSearchFrom,
                testSearchTo,
                PageRequest.of(0, 20)
        );
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetweenTest() throws ParseException {
        Date testSearchFrom = dateFormat.parse("07.09.1990");
        Date testSearchTo = dateFormat.parse("07.09.1993");

        Page<Person> page = personRepository.findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween(
                "testName",
                "testLastName",
                "Russia",
                "Moscow",
                testSearchFrom,
                testSearchTo,
                PageRequest.of(0, 20)
        );
        Assert.assertEquals(1, page.getTotalElements());
    }
}
