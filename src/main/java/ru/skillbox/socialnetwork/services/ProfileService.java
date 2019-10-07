/** @Author Savva */
package ru.skillbox.socialnetwork.services;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        //TODO: Доделать setPhoto, setCity, setCountry
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

    public Person getPersonById(Integer id) {
        //TODO: Сделать поиск людей по ID
        return new Person();
    }

    public ArrayList<Post> getWallPostsById(Integer id, Integer offset, Integer itemPerPage) {
        //TODO: Сделать получение записей на стене по ID пользователя
        return new ArrayList<>();
    }

    public void addWallPostById(Integer id, Date publish_date) {
        //TODO: Сделать добавление записи на стену по ID пользователя
    }

    public ArrayList<Person> searchPerson(String first_name, String last_name, Integer age_from, Integer age_to,
                                          Integer country_id, Integer city_id, Integer offset, Integer itemPerPage) {
        //TODO: Сделать поиск пользователей по параметрам
        return new ArrayList<>();
    }

    public void blockPersonById(Integer id) {
        //TODO: Сделать блокировку пользователя по ID
    }

    public void unblockPersonById(Integer id) {
        //TODO: Сделать разблокировку пользователя по ID
    }

}
