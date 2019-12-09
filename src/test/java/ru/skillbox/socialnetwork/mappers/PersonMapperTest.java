package ru.skillbox.socialnetwork.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
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

        Integer expectedId = 1;
        Assert.assertEquals(expectedId, personMapper.getId());

        String expectedFirstName = "Ivanov";
        Assert.assertEquals(expectedFirstName, personMapper.getFirstName());

        String expectedLastName = "Petr";
        Assert.assertEquals(expectedLastName, personMapper.getLastName());

        Long expectedRegDate = 1559685600000l;
        Assert.assertEquals(expectedRegDate, personMapper.getRegDate());

        Long expectedBirthDate = 633826800000l;
        Assert.assertEquals(expectedBirthDate, personMapper.getBirthDate());

        String expectedEmail = "ivanov@mail.com";
        Assert.assertEquals(expectedEmail, personMapper.geteMail());

        String expectedPhone = "1234567890";
        Assert.assertEquals(expectedPhone, personMapper.getPhone());

        String expectedPhoto = "photo";
        Assert.assertEquals(expectedPhoto, personMapper.getPhoto());

        String expectedAbout = "about";
        Assert.assertEquals(expectedAbout, personMapper.getAbout());

        String expectedCountryTitle = "Russia";
        Assert.assertEquals(expectedCountryTitle, personMapper.getCountry());

        String expectedCityTitle = "Moscow";
        Assert.assertEquals(expectedCityTitle, personMapper.getCity());

        String expectedMessagePermission = "FRIENDS";
        Assert.assertEquals(expectedMessagePermission, personMapper.getMessagesPermission().toString());

        Long expectedLastOnLine = 1571781600000l;
        Assert.assertEquals(expectedLastOnLine, personMapper.getLastOnlineTime());

        boolean expectedIsBlocked = false;
        Assert.assertEquals(expectedIsBlocked, personMapper.getBlocked());
    }
}
