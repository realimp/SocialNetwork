package ru.skillbox.socialnetwork.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProfileService profileService;

    @Test
    public void getPersonByIdTest() {

        Person person = new Person();
        person.setId(9090);
        person.setFirstName("Vasiliy");
        person.setLastName("Galkin");
        person.setPhone("+79163202121");
        person.setPassword("PASSWORD");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(false);
        person.setDeleted(false);

        personRepository.save(person);

        Person actualPerson = profileService.getPersonById(9090);
        Person expectedPerson = personRepository.getOne(9090);
        org.junit.Assert.assertEquals(expectedPerson, actualPerson);

        personRepository.delete(person);
    }

    @Test
    public void searchPersonTest() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Person person1 = new Person();
        person1.setId(9090);
        person1.setFirstName("Vasiliy");
        person1.setLastName("Galkin");
        person1.setPhone("+79163202121");
        person1.setPassword("PASSWORD");
        person1.setBirthDate(dateFormat.parse("30.05.1985"));
        person1.setApproved(true);
        person1.setBlocked(false);
        person1.setOnline(false);
        person1.setDeleted(false);

        Person person2 = new Person();
        person2.setId(9091);
        person2.setFirstName("Vasiliy");
        person2.setLastName("Galkin");
        person2.setPhone("+79163202121");
        person2.setPassword("PASSWORD");
        person2.setBirthDate(dateFormat.parse("18.11.1980"));
        person2.setApproved(true);
        person2.setBlocked(false);
        person2.setOnline(false);
        person2.setDeleted(false);

        personRepository.save(person1);
        personRepository.save(person2);

        List<Person> expectedList = profileService.searchPerson("Vasiliy", "Galkin", 30,
                40, "Moscow","Russia", 0, 10);

        System.out.println("Person list:");
        for (Person person : expectedList) {
            System.out.println("id: "+person.getId()+" fname: "+person.getFirstName()+" lname: "+person.getLastName()+
                    " bdate: "+dateFormat.format(person.getBirthDate())+" country: "+person.getCountry()+" city: "+
                    person.getCity());
        }

        List<Person> actualList = new ArrayList<>();
        actualList.add(person1);
        actualList.add(person2);

        org.junit.Assert.assertEquals(expectedList, actualList);

        personRepository.delete(person1);
        personRepository.delete(person2);
    }

    @Test
    public void blockPersonByIdTest() {

        Person person = new Person();
        person.setId(9090);
        person.setFirstName("Vasiliy");
        person.setLastName("Galkin");
        person.setPhone("+79163202121");
        person.setPassword("PASSWORD");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(false);
        person.setDeleted(false);

        personRepository.save(person);

        profileService.blockPersonById(9090);
        org.junit.Assert.assertTrue(personRepository.getOne(9090).getBlocked());

        personRepository.delete(person);
    }

    @Test
    public void unblockPersonByIdTest() {

        Person person = new Person();
        person.setId(9090);
        person.setFirstName("Vasiliy");
        person.setLastName("Galkin");
        person.setPhone("+79163202121");
        person.setPassword("PASSWORD");
        person.setApproved(true);
        person.setBlocked(true);
        person.setOnline(false);
        person.setDeleted(false);

        personRepository.save(person);

        profileService.unblockPersonById(9090);
        org.junit.Assert.assertFalse(personRepository.getOne(9090).getBlocked());

        personRepository.delete(person);
    }



}
