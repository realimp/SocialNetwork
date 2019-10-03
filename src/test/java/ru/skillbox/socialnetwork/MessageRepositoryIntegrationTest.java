package ru.skillbox.socialnetwork;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Message;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.MessageRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class MessageRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private MessageRepository messageRepository;

  @Test
  public void whenFindById_thenReturnMessageText() {
    // given
    Person person1 = new Person();
    person1.setFirstName("pers1FirstName");
    person1.setLastName("pers1LastName");
    person1.setPhone("11111");
    person1.setPassword("1");
    person1.setApproved(true);
    person1.setBlocked(false);
    person1.setDeleted(false);
    person1.setOnline(false);

    Person person2 = new Person();
    person2.setFirstName("pers2FirstName");
    person2.setLastName("pers2LastName");
    person2.setPhone("22222");
    person2.setPassword("2");
    person2.setApproved(true);
    person2.setBlocked(false);
    person2.setDeleted(false);
    person2.setOnline(false);

    Person person3 = new Person();
    person3.setFirstName("pers3FirstName");
    person3.setLastName("pers3LastName");
    person3.setPhone("33333");
    person3.setPassword("3");
    person3.setApproved(true);
    person3.setBlocked(false);
    person3.setDeleted(false);
    person3.setOnline(false);

    Message message1 = new Message();
    //message1.setId(1);
    message1.setAuthorId(1);
    message1.setRecipientId(2);
    message1.setMessageText("messageText1");
    message1.setDialogId(1);
    message1.setDeleted(false);

    Message message2 = new Message();
    //message2.setId(2);
    message2.setAuthorId(1);
    message2.setRecipientId(2);
    message2.setMessageText("messageText2");
    message2.setDialogId(2);
    message2.setDeleted(false);

    Message message3 = new Message();
    //message1.setId(3);
    message3.setAuthorId(1);
    message3.setRecipientId(3);
    message3.setMessageText("messageText3");
    message3.setDialogId(3);
    message3.setDeleted(false);

    entityManager.persist(person1);
    entityManager.persist(person2);
    entityManager.persist(person3);
    entityManager.persist(message1);
    entityManager.persist(message2);
    entityManager.persist(message3);
    entityManager.flush();

    // when
    Message found = messageRepository.getOne(1);
    // then
    assert (found.getMessageText()).equals("messageText1");
  }
}
