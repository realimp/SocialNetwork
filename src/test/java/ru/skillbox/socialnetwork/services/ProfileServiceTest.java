package ru.skillbox.socialnetwork.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.api.requests.EditPerson;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.PersonsWallPost;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PostRepository postRepository;

    @WithMockUser(username = "test@email.com")
    @Test
    public void getPersonTest() {
        PersonResponse person = profileService.getPerson();
        Assert.assertEquals(Integer.valueOf(4), person.getId());
        Assert.assertEquals("testName", person.getFirstName());
        Assert.assertEquals("testLastName", person.getLastName());
        Assert.assertEquals(Long.valueOf(725068800000l), person.getBirthDate());
        Assert.assertEquals("Russia", person.getCountry());
        Assert.assertEquals("Moscow", person.getCity());
        Assert.assertEquals("+7800200600", person.getPhone());
        Assert.assertEquals("test@email.com", person.geteMail());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void editPersonTest() {
        EditPerson editPerson = new EditPerson();
        editPerson.setFirstName("testName");
        editPerson.setLastName("testLastName");
        editPerson.setAbout("");
        editPerson.setMessagesPermission("ALL");
        editPerson.setCountry("USA");
        editPerson.setCity("Moscow");
        editPerson.setPhone("+7999999999");
        editPerson.setBirthDate(new Date(725068800000l));
        PersonResponse person = profileService.editPerson(editPerson);
        Assert.assertEquals(Integer.valueOf(4), person.getId());
        Assert.assertEquals("USA", person.getCountry());
        Assert.assertEquals("+7999999999", person.getPhone());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void deletePersonTest() {
        Assert.assertEquals("ok", profileService.deletePerson().getMessage());
        Assert.assertEquals(true, personRepository.findByEMail("test@email.com").getDeleted());
    }

    @Test
    public void getPersonByIdTest() {
        PersonResponse personResponse = profileService.getPersonById(4);
        Assert.assertEquals(Integer.valueOf(4), personResponse.getId());
        Assert.assertEquals("testName", personResponse.getFirstName());
        Assert.assertEquals("testLastName", personResponse.getLastName());
        Assert.assertEquals(Long.valueOf(725068800000l), personResponse.getBirthDate());
        Assert.assertEquals("Russia", personResponse.getCountry());
        Assert.assertEquals("Moscow", personResponse.getCity());
        Assert.assertEquals("+7800200600", personResponse.getPhone());
        Assert.assertEquals("test@email.com", personResponse.geteMail());
    }

    @Test
    public void getWallPostByIdTest() {
        List<PersonsWallPost> list = profileService.getWallPostsById(2, 0, 10);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void addWallPostByIdTest() throws ParseException {
        profileService.addWallPostById(4, new Date(), "POST TITLE", "POST TEXT", null);
        List<PersonsWallPost> list = profileService.getWallPostsById(4, 0, 10);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void searchPersonTest() throws ParseException {
        List<PersonResponse> list = profileService.searchPerson("testName", "testLastName", 10,
                40, "Russia", "Moscow", 0, 10);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void blockPersonByIdTest() {
        profileService.blockPersonById(4);
        Assert.assertTrue(profileService.getPersonById(4).getBlocked());
    }

    @Test
    public void unblockPersonByIdTest() {
        profileService.unblockPersonById(4);
        Assert.assertFalse(profileService.getPersonById(4).getBlocked());
    }

    @After
    public void cleanUp() {
        Person person = personRepository.findByEMail("test@email.com");
        person.setCountry("Russia");
        person.setPhone("+7800200600");
        person.setDeleted(false);
        person.setBlocked(false);
        personRepository.saveAndFlush(person);
        Page<Post> byAuthorId = postRepository.findByAuthorId(4, PageRequest.of(0, 1));
        if (byAuthorId.hasContent()) {
            Post post = byAuthorId.getContent().get(0);
            postRepository.delete(post);
        }
    }
}
