package ru.skillbox.socialnetwork.mappers;

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
        personResponse.setRegDate(person.getRegDate() != null ? person.getRegDate().getTime() : null);
        personResponse.setBirthDate(person.getBirthDate() != null ? person.getBirthDate().getTime() : null);
        personResponse.seteMail(person.getEMail());
        personResponse.setPhone(person.getPhone());
        personResponse.setPhoto(person.getPhoto());
        personResponse.setAbout(person.getAbout());
        personResponse.setCity(person.getCity());
        personResponse.setCountry(person.getCountry());
        personResponse.setMessagesPermission(MessagePermission.valueOf(person.getMessagesPermission() != null ?
                person.getMessagesPermission() : "ALL"));
        personResponse.setLastOnlineTime(person.getLastOnlineTime() != null ? person.getLastOnlineTime().getTime() : null);
        personResponse.setBlocked(person.getBlocked());

        return personResponse;
    }
}
