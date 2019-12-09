package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.responses.BasicPerson;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.entities.MessagePermission;
import ru.skillbox.socialnetwork.entities.Person;

public class PersonToBasicPersonMapper {
    private PersonToBasicPersonMapper(){}

    public static BasicPerson getBasicPerson(Person person){
        BasicPerson basicPerson = new BasicPerson();

        basicPerson.setId(person.getId());
        basicPerson.setFirstName(person.getFirstName());
        basicPerson.setLastName(person.getLastName());
        basicPerson.setPhoto(person.getPhoto());
        basicPerson.setLastOnlineTime(person.getLastOnlineTime() != null ? person.getLastOnlineTime().getTime() : null);
        return basicPerson;
    }

}
