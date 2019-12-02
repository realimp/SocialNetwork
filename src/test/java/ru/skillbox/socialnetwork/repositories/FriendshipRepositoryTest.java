package ru.skillbox.socialnetwork.repositories;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Friendship;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private PersonRepository personRepository;

    private int personSize;
    private int friendshipSize;

    @Before
    public void before() {
        personSize = personRepository.findAll().size();
        friendshipSize = friendshipRepository.findAll().size();
    }

    private Friendship addAndAssert(Integer id, Person dstPerson, Person srcPerson, FriendshipStatus status) {
        Friendship friendship = new Friendship();
        int sizeBeforeAddingFriendship = friendshipRepository.findAll().size();
        friendship.setCode(status);
        friendship.setDstPerson(dstPerson);
        friendship.setSrcPerson(srcPerson);
        friendship.setId(id);
        friendshipRepository.saveAndFlush(friendship);
        assertNotNull(friendship.getId());
        assertFriendship(friendship.getId(), dstPerson, srcPerson, status, friendship);
        if (id == null) sizeBeforeAddingFriendship++;
        assertEquals(sizeBeforeAddingFriendship, friendshipRepository.findAll().size());
        return friendship;
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

    @Test
    public void addFriendshipTest() {
        Person dstPerson = personRepository.getOne(1);
        Person srcPerson = personRepository.getOne(3);
        addAndAssert(null, dstPerson, dstPerson, FriendshipStatus.FRIEND);
        addAndAssert(null, dstPerson, srcPerson, FriendshipStatus.REQUEST);
        addAndAssert(null, srcPerson, dstPerson, FriendshipStatus.BLOCKED);
    }

    @Test
    public void updateFriendshipTest() {
        Person person = addPerson("POlegka", "SPetrovskii", "8881112269", "POleGka@mail.ru");
        Person friend = addPerson("POlegkah", "SPetrovskii", "8881112259", "POleGkah@mail.ru");
        Friendship friendshipToUpdate = addAndAssert(null, person, friend, FriendshipStatus.REQUEST);
        Person oldSrcPerson = friendshipToUpdate.getSrcPerson();
        Person oldDstPerson = friendshipToUpdate.getDstPerson();
        FriendshipStatus status = friendshipToUpdate.getCode();
        Person dstPerson = addPerson("Oleg", "Petrov", "8881112266", "OleG@mail.ru");
        Person srcPerson = addPerson("Olega", "Petrova", "8881112267", "OleGa@mail.ru");
        addAndAssert(friendshipToUpdate.getId(), dstPerson, srcPerson, FriendshipStatus.DECLINED);
        addAndAssert(friendshipToUpdate.getId(), oldDstPerson, oldSrcPerson, status);
    }

    @Test
    public void deleteFriendshipTest() {
        Person person = personRepository.getOne(2);
        Friendship friendship = addAndAssert(null, person, person, FriendshipStatus.SUBSCRIBED);
        int sizeFriendship = friendshipRepository.findAll().size();
        friendshipRepository.delete(friendship);
        assertFalse(friendshipRepository.findById(friendship.getId()).isPresent());
        assertEquals(sizeFriendship - 1, friendshipRepository.findAll().size());
    }

    @Test
    public void findByFriendsTest() {
        List<Person> personList = personRepository.findAll();
        Person person = addPerson("Olegka", "Petrovskii", "8881112268", "OleGka@mail.ru");
        FriendshipStatus status = FriendshipStatus.FRIEND;
        for (Person p : personList)
            addAndAssert(null, person, p, status);
        assertEquals(personList.size(), friendshipRepository.findByFriends(person, status).size());
    }

    @Test
    public void findByFriendTest() {
        Person person = addPerson("POlegka", "SPetrovskii", "8881112269", "POleGka@mail.ru");
        Person friend = addPerson("POlegkah", "SPetrovskii", "8881112259", "POleGkah@mail.ru");
        Friendship friendship = addAndAssert(null, person, friend, FriendshipStatus.REQUEST);
        Friendship actualFriendship = friendshipRepository.findByFriend(person, friend);
        assertFriendship(friendship.getId(), friendship.getDstPerson(), friendship.getSrcPerson(), friendship.getCode(), actualFriendship);
    }

    @Test
    public void findRecommendationsTest() {
        List<Person> addedPerson = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            Person person = addPerson("POlegka" + i, "SPetrovskii", "888111229" + i, i + "POleGka@mail.ru");
            addedPerson.add(person);
        }
        Person user = addedPerson.get(1);
        List<Person> userFriends = new ArrayList<>();
        userFriends.add(addedPerson.get(0));
        userFriends.add(addedPerson.get(5));
        userFriends.add(addedPerson.get(6));
        addAndAssert(null, user, addedPerson.get(0), FriendshipStatus.FRIEND);
        addAndAssert(null, user, addedPerson.get(5), FriendshipStatus.DECLINED);
        addAndAssert(null, user, addedPerson.get(6), FriendshipStatus.BLOCKED);
        addAndAssert(null, addedPerson.get(2), addedPerson.get(6), FriendshipStatus.REQUEST);
        addAndAssert(null, addedPerson.get(2), addedPerson.get(4), FriendshipStatus.BLOCKED);
        addAndAssert(null, addedPerson.get(0), addedPerson.get(3), FriendshipStatus.REQUEST);
        addAndAssert(null, addedPerson.get(0), user, FriendshipStatus.SUBSCRIBED);
        addAndAssert(null, addedPerson.get(0), addedPerson.get(4), FriendshipStatus.DECLINED);
        addAndAssert(null, addedPerson.get(5), addedPerson.get(3), FriendshipStatus.BLOCKED);
        addAndAssert(null, addedPerson.get(5), addedPerson.get(7), FriendshipStatus.REQUEST);
        addAndAssert(null, addedPerson.get(5), addedPerson.get(9), FriendshipStatus.SUBSCRIBED);
        addAndAssert(null, addedPerson.get(5), addedPerson.get(10), FriendshipStatus.FRIEND);
        addAndAssert(null, addedPerson.get(6), addedPerson.get(4), FriendshipStatus.BLOCKED);
        addAndAssert(null, addedPerson.get(6), addedPerson.get(11), FriendshipStatus.REQUEST);
        addAndAssert(null, addedPerson.get(6), addedPerson.get(12), FriendshipStatus.SUBSCRIBED);
        List<Integer> recommendations = friendshipRepository.findRecommendations(user.getId());
        System.out.println(recommendations);
        assertTrue(recommendations.size() == 5);
        for (Integer r : recommendations) {
            assertNull(friendshipRepository.findByFriend(user, personRepository.getOne(r)));
            assertTrue(userFriends.stream()
                    .map(p -> friendshipRepository.findByFriend(p, personRepository.getOne(r)))
                    .anyMatch(Objects::nonNull));
        }
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
}
