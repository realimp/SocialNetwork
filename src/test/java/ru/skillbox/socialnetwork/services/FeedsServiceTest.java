package ru.skillbox.socialnetwork.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skillbox.socialnetwork.api.responses.Feeds;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;

import javax.transaction.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeedsServiceTest {

    @Autowired
    private FeedsService feedsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonRepository personRepository;

    private Person person = new Person();

    @Autowired
    private MockMvc mockMvc;

    @Before
//    @Transactional
    public void before() {
        person.setConfirmationCode("Code123");
        person.setFirstName("firstName");
        person.setEMail("Code123@email.com");
        person.setPassword("pass1");
        person.setLastName("lastName");
        person.setApproved(true);
        person.setBlocked(false);
        person.setDeleted(false);
        person.setOnline(false);
        person.setPhone("");
        personRepository.saveAndFlush(person);
    }

    @Test
    @WithMockUser(username = "Code123@email.com")
    public void getFeedsTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/feeds")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals("{\"data\":[{\"id\":3,\"time\":1571752800000,\"author\":{\"id\":3,\"first_name\":\"Petr\",\"last_name\":\"Nevolnyj\"},\"title\":\"Мир музыки\",\"likes\":1,\"tags\":[\"Backend hasn't have entity for post tag\"],\"comments\":[{}],\"post_text\":\"Всем привет, сегодня мы поговорим об особенностях звучания различных электрогитар...\",\"is_blocked\":false,\"my_like\":false},{\"id\":2,\"time\":1571749200000,\"author\":{\"id\":2,\"first_name\":\"Oleg\",\"last_name\":\"Popov\"},\"title\":\"Непутевые заметки\",\"likes\":1,\"tags\":[\"Backend hasn't have entity for post tag\"],\"comments\":[{}],\"post_text\":\"Здесь я хотел бы рассказать о последнем путешествии...\",\"is_blocked\":false,\"my_like\":false},{\"id\":1,\"time\":1571745600000,\"author\":{\"id\":1,\"first_name\":\"Pavel\",\"last_name\":\"Dobromirov\"},\"title\":\"First post ever!\",\"likes\":1,\"tags\":[\"Backend hasn't have entity for post tag\"],\"comments\":[{}],\"post_text\":\"Welcome everybody! Our social network has opened!\",\"is_blocked\":false,\"my_like\":false}],\"total\":3,\"offset\":0,\"perPage\":20}",
                mvcResult.getResponse().getContentAsString(), false);
    }

    @After
    public void after(){
        personRepository.delete(person);
    }

}
