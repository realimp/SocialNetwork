package ru.skillbox.socialnetwork.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.entities.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonMapperTest {

    @Test
    public void getMappingTest() throws JsonProcessingException, JSONException, ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date testPersonBirthDate = dateFormat.parse("01.02.1990");
        Date testPersonRegDate = dateFormat.parse("05.06.2019");
        Date testPersonLastOnLineDate = dateFormat.parse("23.10.2019");

        Person person = new Person();
        person.setId(1);
        person.setFirstName("Ivanov");
        person.setLastName("Petr");
        person.setRegDate(testPersonRegDate);
        person.setBirthDate(testPersonBirthDate);
        person.setEMail("ivanov@mail.com");
        person.setPhone("1234567890");
        person.setPhoto("photo");
        person.setAbout("about");
        person.setCity("Moscow");
        person.setCountry("Russia");
        person.setMessagesPermission("FRIENDS");
        person.setLastOnlineTime(testPersonLastOnLineDate);
        person.setBlocked(false);
        PersonResponse personMapper = PersonMapper.getMapping(person);

        String expected = "{id:1," +
                "first_name:\"Ivanov\"," +
                "last_name:\"Petr\"," +
                "reg_date:1559685600000," +
                "birth_date:633826800000," +
                "email:\"ivanov@mail.com\"," +
                "phone:\"1234567890\"," +
                "photo:\"photo\"," +
                "about:\"about\"," +
                "city:{id:1," +
                "title:\"Moscow\"}," +
                "country:{id:1," +
                "title:\"Russia\"}," +
                "messages_permission:\"FRIENDS\"," +
                "last_online_time:1571781600000," +
                "is_blocked:false}";
        JSONAssert.assertEquals(expected, new ObjectMapper().writeValueAsString(personMapper), true);
    }
}
