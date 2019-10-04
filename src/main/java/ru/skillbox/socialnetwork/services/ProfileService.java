package ru.skillbox.socialnetwork.services;

import org.hibernate.dialect.Ingres9Dialect;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.Date;

@Service
public class ProfileService {

    private Person person;

    public ProfileService(Person person) {
        this.person = person;
    }


    public Person getPerson() {
        return person;
    }

    public void editPerson(String first_name, String last_name, Date birth_date, String phone, String photo_id,
                           String about, Integer town_id, Integer country_id, String messages_permission) {
        person.setFirstName(first_name);
        person.setLastName(last_name);
        person.setBirthDate(birth_date);
        person.setPhone(phone);
        //person.setPhoto(photo_id);
        person.setAbout(about);
        //person.setCity(town_id);
        //person.setCountry(country_id);
        person.setMessagesPermission(messages_permission);
    }

    public void deletePerson() {
        person.setDeleted(true);
    }

    public void getPersonById(Integer Id) {
        /*
        Откуда берем пользователя?
         */
    }

    public void getWallListById(Integer Id, ) {
        /*
        Откуда берем записи на стене?
         */
    }





}
