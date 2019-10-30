package ru.skillbox.socialnetwork.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.security.CustomUserDetailsService;
import ru.skillbox.socialnetwork.security.SecurityConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SecurityConfig.class)
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /*
    -- before running add test data to DB:
    INSERT INTO social_network.person(id, first_name, last_name, birth_date, phone, password, city, country, is_approved, is_blocked) values (90901, "Stefan", "Radzhinskij", "1985-05-29 00:00:00", "+79163202121", "PASSWORD", "Moscow", "Russia", 1, 0);
    INSERT INTO social_network.person(id, first_name, last_name, birth_date, phone, password, city, country, is_approved, is_blocked) values (90902, "Stefan", "Radzhinskij", "1983-12-07 00:00:00", "+79163202321", "PASSWORD", "Moscow", "Russia", 1, 1);
    INSERT INTO social_network.person(id, first_name, last_name, birth_date, phone, password, city, country, is_approved, is_blocked) values (90903, "Stefan", "Radzhinskij", "1980-01-03 00:00:00", "+79163202111", "PASSWORD", "Moscow", "Russia", 1, 0);

    -- after running delete test data from DB:
    delete from social_network.person
    where id in (90901, 90902, 90903);
    */

    @Test
    public void getPersonByIdTest() {
        PersonResponse personResponse = profileService.getPersonById(1);
        String email = "paveldobro92@mail.ru";
        Person person = (Person) customUserDetailsService.loadUserByUsername(email);
        org.junit.Assert.assertTrue(personResponse.getId() == 1);
        org.junit.Assert.assertTrue(personResponse.getFirstName().equals("Pavel"));
        org.junit.Assert.assertTrue(personResponse.getLastName().equals("Dobromirov"));
        org.junit.Assert.assertTrue(personResponse.getPhone().equals("+79031119090"));
    }

    @Test
    public void searchPersonTest() throws ParseException {
        List<PersonResponse> list = profileService.searchPerson("Stefan", "Radzhinskij", 30,
                40, "Russia", "Moscow", 0, 10);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println("Person list:");
        for (PersonResponse person : list) {
            System.out.println("id: "+person.getId()+" fname: "+person.getFirstName()+" lname: "+person.getLastName()+
                    " bdate: "+dateFormat.format(person.getBirthDate())+" country: "+person.getCountry()+" city: "+
                    person.getCity());
        }

        org.junit.Assert.assertTrue(list.size() == 3);
    }

    @Test
    public void blockPersonByIdTest() {
        profileService.blockPersonById(90901);
        org.junit.Assert.assertTrue(profileService.getPersonById(90901).getBlocked());
    }

    @Test
    public void unblockPersonByIdTest() {
        profileService.unblockPersonById(90902);
        org.junit.Assert.assertFalse(profileService.getPersonById(90902).getBlocked());
    }

}
