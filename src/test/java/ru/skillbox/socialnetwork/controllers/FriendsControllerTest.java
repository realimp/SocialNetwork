package ru.skillbox.socialnetwork.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.Friendship;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.mappers.PersonMapper;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FriendsControllerTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MockMvc mockMvc;

    private int personSize;
    private int friendshipSize;
    private List<Person> addedPerson;

    public FriendsControllerTest() {
    }

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

    private String getPersonData(Person person) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString((PersonMapper.getMapping(person)));
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

    public void personAuth(Person person) {
    }

    @Test
    public void withoutAuthTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/friends")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(403)).andReturn();
        assertEquals("Access Denied", result.getResponse().getErrorMessage());

        result = this.mockMvc.perform(get("/friends/recommendations")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(403)).andReturn();
        assertEquals("Access Denied", result.getResponse().getErrorMessage());

        result = this.mockMvc.perform(get("/friends/request")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(403)).andReturn();
        assertEquals("Access Denied", result.getResponse().getErrorMessage());

        result = this.mockMvc.perform(delete("/friends/{id}", addedPerson.get(0).getId())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(403)).andReturn();
        assertEquals("Access Denied", result.getResponse().getErrorMessage());

        result = this.mockMvc.perform(post("/friends/{id}", addedPerson.get(0).getId())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(403)).andReturn();
        assertEquals("Access Denied", result.getResponse().getErrorMessage());

        UserIds userIds = new UserIds();
        userIds.setIds(new int[]{1, 2});
        result = this.mockMvc.perform(post("/is/friends")
                .contentType(APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(userIds)))
                .andDo(print())
                .andExpect(status().is(403)).andReturn();
        assertEquals("Access Denied", result.getResponse().getErrorMessage());
    }

    @Test
    @WithMockUser(username = "1POleGka@mail.ru")
    public void getFriendsTest1() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/friends")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[" +
                        getPersonData(addedPerson.get(0)) + "," +
                        getPersonData(addedPerson.get(5)) + "," +
                        getPersonData(addedPerson.get(6)) + "," +
                        getPersonData(addedPerson.get(7)) + "," +
                        getPersonData(addedPerson.get(8)) + "," +
                        getPersonData(addedPerson.get(9)) + "],\"total\":6,\"offset\":0,\"perPage\":0}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "3POleGka@mail.ru")
    public void getFriendsTest2() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/friends")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[],\"total\":0,\"offset\":0,\"perPage\":0}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "1POleGka@mail.ru")
    public void getRecommendationsTest1() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/friends/recommendations")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference typeRef = new TypeReference<ResponseList<List<PersonResponse>>>() {
        };
        ResponseList<List<PersonResponse>> responseList = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), typeRef);
        assertTrue(new Date().getTime() - responseList.getTimestamp() < 1000);
        responseList.setTimestamp(0);
        List<PersonResponse> personResponseList = responseList.getData();
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < personResponseList.size(); i++) {
            data.append(new ObjectMapper().writeValueAsString(personResponseList.get(i)));
            if (i < personResponseList.size() - 1) data.append(",");
        }
        JSONAssert.assertEquals("{\"error\":null,\"timestamp\":0,\"data\":[" + data.toString()
                        + "],\"total\":5,\"offset\":0,\"perPage\":0}",
                new ObjectMapper().writeValueAsString(responseList), true);
    }

    @Test
    @WithMockUser(username = "3POleGka@mail.ru")
    public void getRecommendationsTest2() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/friends/recommendations")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[],\"total\":0,\"offset\":0,\"perPage\":0}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "5POleGka@mail.ru")
    public void getFriendsRequestTest1() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/friends/request")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[" + getPersonData(addedPerson.get(7))
                        + "],\"total\":1,\"offset\":0,\"perPage\":0}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "3POleGka@mail.ru")
    public void getFriendsRequestTest2() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/friends/request")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[],\"total\":0,\"offset\":0,\"perPage\":0}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "0POleGka@mail.ru")
    public void deleteFriendsTest1() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(delete("/friends/{id}", "")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(405)).andReturn();
        assertEquals("Request method 'DELETE' not supported", mvcResult.getResponse().getErrorMessage());

        int id = addedPerson.get(12).getId() + 1;
        mvcResult = this.mockMvc.perform(delete("/friends/{id}", id)
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"error\":\"Не удалось определить пользователя с идентификатором " + id + "\"}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "1POleGka@mail.ru")
    public void deleteFriendsTest2() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(delete("/friends/{id}", addedPerson.get(2).getId())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"error\":\"" +
                        "Пользователь " + addedPerson.get(2).getEMail() + " не является другом для пользователя "
                        + addedPerson.get(1).getEMail() + "\"}",
                mvcResult.getResponse().getContentAsString(), false);

        mvcResult = this.mockMvc.perform(delete("/friends/{id}", addedPerson.get(0).getId())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":{\"message\":\"ok\"}}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "0POleGka@mail.ru")
    public void addFriendsTest1() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/friends/{id}", "")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(405)).andReturn();
        assertEquals("Request method 'POST' not supported", mvcResult.getResponse().getErrorMessage());

        int id = addedPerson.get(12).getId() + 1;
        mvcResult = this.mockMvc.perform(post("/friends/{id}", id)
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"error\":\"Не удалось определить пользователя с идентификатором " + id + "\"}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "1POleGka@mail.ru")
    public void addFriendsTest2() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/friends/{id}", addedPerson.get(0).getId())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"error\":\"" +
                        "Пользователь " + addedPerson.get(0).getEMail() + " уже является другом для пользователя "
                        + addedPerson.get(1).getEMail() + "\"}",
                mvcResult.getResponse().getContentAsString(), false);

        mvcResult = this.mockMvc.perform(post("/friends/{id}", addedPerson.get(1).getId())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"error\":\"" +
                        "Пользователь " + addedPerson.get(1).getEMail() + " не может быть сам себе другом\"}",
                mvcResult.getResponse().getContentAsString(), false);

        mvcResult = this.mockMvc.perform(post("/friends/{id}", addedPerson.get(2).getId())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":{\"message\":\"ok\"}}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    @WithMockUser(username = "1POleGka@mail.ru")
    public void checkFriendsTest() throws Exception {
        UserIds userIds = new UserIds();
        userIds.setIds(new int[]{addedPerson.get(12).getId() + 1});
        MvcResult mvcResult = this.mockMvc.perform(post("/is/friends")
                .content(new ObjectMapper().writeValueAsString(userIds))
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[]}", mvcResult.getResponse().getContentAsString(), false);

        userIds.setIds(new int[]{addedPerson.get(2).getId(), addedPerson.get(0).getId(), addedPerson.get(5).getId()});
        mvcResult = this.mockMvc.perform(post("/is/friends")
                .content(new ObjectMapper().writeValueAsString(userIds))
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[{\"status\":\"FRIEND\",\"user_id\":" + addedPerson.get(0).getId() +
                        "},{\"status\":\"DECLINED\",\"user_id\":" + addedPerson.get(5).getId() + "}]}",
                mvcResult.getResponse().getContentAsString(), false);
    }
}
