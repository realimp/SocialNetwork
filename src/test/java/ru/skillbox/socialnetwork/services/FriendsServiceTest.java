package ru.skillbox.socialnetwork.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.entities.Friendship;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendsServiceTest {

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private PersonRepository personRepository;

    private int personSize;
    private int friendshipSize;
    private List<Person> addedPerson;

    @Before
    public void before() {
        personSize = personRepository.findAll().size();
        friendshipSize = friendshipRepository.findAll().size();
        addedPerson = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            Person person = addPerson("POlegka" + i, "SPetrovskii" + i, "888111229" + i, i + "POleGka@mail.ru");
            addedPerson.add(person);
        }
        Person user = addedPerson.get(1);
        addAndAssert(user, addedPerson.get(0), FriendshipStatus.FRIEND);
        addAndAssert(user, addedPerson.get(5), FriendshipStatus.DECLINED);
        addAndAssert(user, addedPerson.get(6), FriendshipStatus.BLOCKED);
        addAndAssert(user, addedPerson.get(7), FriendshipStatus.SUBSCRIBED);
        addAndAssert(user, addedPerson.get(8), FriendshipStatus.REQUEST);
        addAndAssert(user, addedPerson.get(9), FriendshipStatus.FRIEND);
        addAndAssert(addedPerson.get(2), addedPerson.get(6), FriendshipStatus.REQUEST);
        addAndAssert(addedPerson.get(2), addedPerson.get(4), FriendshipStatus.BLOCKED);
        addAndAssert(addedPerson.get(0), addedPerson.get(3), FriendshipStatus.REQUEST);
        addAndAssert(addedPerson.get(0), user, FriendshipStatus.SUBSCRIBED);
        addAndAssert(addedPerson.get(0), addedPerson.get(4), FriendshipStatus.DECLINED);
        addAndAssert(addedPerson.get(5), addedPerson.get(3), FriendshipStatus.BLOCKED);
        addAndAssert(addedPerson.get(5), addedPerson.get(7), FriendshipStatus.REQUEST);
        addAndAssert(addedPerson.get(5), addedPerson.get(9), FriendshipStatus.SUBSCRIBED);
        addAndAssert(addedPerson.get(5), addedPerson.get(10), FriendshipStatus.FRIEND);
        addAndAssert(addedPerson.get(6), addedPerson.get(4), FriendshipStatus.BLOCKED);
        addAndAssert(addedPerson.get(6), addedPerson.get(11), FriendshipStatus.REQUEST);
        addAndAssert(addedPerson.get(6), addedPerson.get(12), FriendshipStatus.SUBSCRIBED);
    }

    private void addAndAssert(Person dstPerson, Person srcPerson, FriendshipStatus status) {
        Friendship friendship = new Friendship();
        int sizeBeforeAddingFriendship = friendshipRepository.findAll().size();
        friendship.setCode(status);
        friendship.setDstPerson(dstPerson);
        friendship.setSrcPerson(srcPerson);
        friendship.setId(null);
        friendshipRepository.saveAndFlush(friendship);
        assertNotNull(friendship.getId());
        assertFriendship(friendship.getId(), dstPerson, srcPerson, status, friendship);
        assertEquals(sizeBeforeAddingFriendship + 1, friendshipRepository.findAll().size());
    }

    private void assertFriendship(Integer id, Person dstPerson, Person srcPerson, FriendshipStatus status, Friendship friendship) {
        assertEquals(id, friendship.getId());
        assertEquals(srcPerson.getId(), friendship.getSrcPerson().getId());
        assertEquals(dstPerson.getId(), friendship.getDstPerson().getId());
        assertEquals(status, friendship.getCode());
    }

    private Person addPerson(String firstName, String lastName, String phone, String email) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPhone(phone);
        person.setEMail(email);
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        personRepository.saveAndFlush(person);
        return person;
    }

    @After
    public void after() {
        friendshipRepository.findAll().stream()
                .filter(p -> p.getId() > friendshipSize)
                .forEach(p -> friendshipRepository.delete(p));

        personRepository.findAll().stream()
                .filter(p -> p.getId() > personSize)
                .forEach(p -> personRepository.delete(p));
    }

    @Test
    @Transactional
    public void getFriendsTest() {
        ResponseList<List<PersonResponse>> response = friendsService.getFriends(null, FriendshipStatus.REQUEST);
        assertTrue(new Date().getTime() - response.getTimestamp() < 1);
        assertEquals("Не удалось определить пользователя с идентификатором null", response.getError());
        assertNull(response.getData());
        assertEquals(0, response.getOffset());
        assertEquals(0, response.getPerPage());
        assertEquals(0, response.getTotal());

        response = friendsService.getFriends(addedPerson.get(1), FriendshipStatus.FRIEND);
        assertNull(response.getError());
        List<PersonResponse> personResponseList = response.getData();
        assertEquals(2, personResponseList.size());
        assertTrue(personResponseList.stream().anyMatch(p -> p.getId().equals(addedPerson.get(0).getId())));
        assertTrue(personResponseList.stream().anyMatch(p -> p.getId().equals(addedPerson.get(9).getId())));
        assertEquals(0, response.getOffset());
        assertEquals(0, response.getPerPage());
        assertEquals(2, response.getTotal());

        response = friendsService.getFriends(addedPerson.get(3), FriendshipStatus.FRIEND);
        assertNull(response.getError());
        assertEquals(0, response.getData().size());
        assertEquals(0, response.getOffset());
        assertEquals(0, response.getPerPage());
        assertEquals(0, response.getTotal());

        response = friendsService.getFriends(addedPerson.get(5), FriendshipStatus.REQUEST);
        assertNull(response.getError());
        personResponseList = response.getData();
        assertEquals(1, personResponseList.size());
        assertTrue(personResponseList.stream().anyMatch(p -> p.getId().equals(addedPerson.get(7).getId())));
        assertEquals(0, response.getOffset());
        assertEquals(0, response.getPerPage());
        assertEquals(1, response.getTotal());
    }

    @Test
    @Transactional
    public void getRecommendationsTest() {
        ResponseList<List<PersonResponse>> response = friendsService.getRecommendations(null);
        assertTrue(new Date().getTime() - response.getTimestamp() < 1);
        assertEquals("Не удалось определить пользователя с идентификатором null", response.getError());
        assertNull(response.getData());
        assertEquals(0, response.getOffset());
        assertEquals(0, response.getPerPage());
        assertEquals(0, response.getTotal());

        response = friendsService.getRecommendations(addedPerson.get(1));
        assertNull(response.getError());
        List<PersonResponse> personResponseList = response.getData();
        assertEquals(5, personResponseList.size());
        for (PersonResponse r : personResponseList) {
            assertNull(friendshipRepository.findByFriend(addedPerson.get(1), personRepository.getOne(r.getId())));
            assertTrue(friendshipRepository.findByFriend(addedPerson.get(0), personRepository.getOne(r.getId())) != null ||
                    friendshipRepository.findByFriend(addedPerson.get(5), personRepository.getOne(r.getId())) != null ||
                    friendshipRepository.findByFriend(addedPerson.get(6), personRepository.getOne(r.getId())) != null ||
                    friendshipRepository.findByFriend(addedPerson.get(7), personRepository.getOne(r.getId())) != null ||
                    friendshipRepository.findByFriend(addedPerson.get(8), personRepository.getOne(r.getId())) != null ||
                    friendshipRepository.findByFriend(addedPerson.get(9), personRepository.getOne(r.getId())) != null);
        }
        assertEquals(0, response.getOffset());
        assertEquals(0, response.getPerPage());
        assertEquals(5, response.getTotal());

        response = friendsService.getRecommendations(addedPerson.get(3));
        assertNull(response.getError());
        assertEquals(0, response.getData().size());
        assertEquals(0, response.getOffset());
        assertEquals(0, response.getPerPage());
        assertEquals(0, response.getTotal());
    }

    @Test
    @Transactional
    public void deleteFriendsTest() {
        String response = friendsService.deleteFriends(null, addedPerson.get(2));
        assertEquals("Не удалось определить пользователя с идентификатором null", response);

        response = friendsService.deleteFriends(addedPerson.get(1), addedPerson.get(2));
        assertEquals("Пользователь " + addedPerson.get(2).getEMail() + " не является другом для пользователя "
                + addedPerson.get(1).getEMail(), response);

        response = friendsService.deleteFriends(addedPerson.get(1), addedPerson.get(0));
        assertNull(response);

        response = friendsService.deleteFriends(addedPerson.get(1), addedPerson.get(0));
        assertEquals("Пользователь " + addedPerson.get(0).getEMail() + " не является другом для пользователя "
                + addedPerson.get(1).getEMail(), response);
    }

    @Test
    @Transactional
    public void addFriendsTest() {
        String response = friendsService.addFriends(null, addedPerson.get(0));
        assertEquals("Не удалось определить пользователя с идентификатором null", response);

        response = friendsService.addFriends(addedPerson.get(1), addedPerson.get(0));
        assertEquals("Пользователь " + addedPerson.get(0).getEMail() + " уже является другом для пользователя "
                + addedPerson.get(1).getEMail(), response);

        response = friendsService.addFriends(addedPerson.get(1), addedPerson.get(1));
        assertEquals("Пользователь " + addedPerson.get(1).getEMail() + " не может быть сам себе другом"
                , response);

        response = friendsService.addFriends(addedPerson.get(1), addedPerson.get(2));
        assertNull(response);

        response = friendsService.addFriends(addedPerson.get(1), addedPerson.get(2));
        assertNull(response);

        response = friendsService.addFriends(addedPerson.get(1), addedPerson.get(2));
        assertEquals("Пользователь " + addedPerson.get(2).getEMail() + " уже является другом для пользователя "
                + addedPerson.get(1).getEMail(), response);
    }

    @Test
    @Transactional
    public void getFriendshipTest() {
        FriendshipStatus response = friendsService.getFriendship(null, 1000);
        assertNull(response);

        response = friendsService.getFriendship(addedPerson.get(1), 1000);
        assertNull(response);

        response = friendsService.getFriendship(addedPerson.get(1), addedPerson.get(2).getId());
        assertNull(response);

        response = friendsService.getFriendship(addedPerson.get(1), addedPerson.get(0).getId());
        assertEquals(FriendshipStatus.FRIEND, response);

        response = friendsService.getFriendship(addedPerson.get(1), addedPerson.get(5).getId());
        assertEquals(FriendshipStatus.DECLINED, response);
    }
}
