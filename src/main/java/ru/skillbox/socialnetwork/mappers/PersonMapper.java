package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.City;
import ru.skillbox.socialnetwork.api.Country;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.entities.MessagePermission;
import ru.skillbox.socialnetwork.entities.Person;

public class PersonMapper {
    private PersonMapper(){}

    public static PersonResponse getMapping(Person person){
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
        personResponse.setCity(new City(1, person.getCity()));
        personResponse.setCountry(new Country(1, person.getCountry()));
        personResponse.setMessagesPermission(MessagePermission.valueOf(person.getMessagesPermission()));
        personResponse.setLastOnlineTime(person.getLastOnlineTime().getTime());
        personResponse.setBlocked(person.getBlocked());

        return personResponse;
    }
}