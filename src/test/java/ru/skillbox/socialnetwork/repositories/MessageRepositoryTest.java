package ru.skillbox.socialnetwork.repositories;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Message;
import ru.skillbox.socialnetwork.entities.Person;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryTest {

  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private PersonRepository personRepository;

  private Person person1 = new Person();
  private Message message1 = new Message();

  private List<Message> messageList = new ArrayList<>();

  @Test
  public void testMessageRepository() {
    person1.setFirstName("pers1 FirstName");
    person1.setLastName("pers1 LastName");
    person1.setPhone("11111");
    person1.setPassword("1");
    person1.setApproved(true);
    person1.setBlocked(false);
    person1.setDeleted(false);
    person1.setOnline(false);

    message1.setRecipient(person1);
    message1.setAuthor(person1);
    message1.setMessageText("messageText1");
    //message1.setDialogId(1);
    message1.setDeleted(false);

    personRepository.save(person1);
    messageRepository.save(message1);
    messageRepository.flush();

    messageList = messageRepository.findAll();
    Assert.assertEquals(1, messageList.size());

    message1.setMessageText("change Text3");
    messageRepository.save(message1);

    Assert.assertEquals("change Text3", messageRepository.getOne(1).getMessageText());

    messageRepository.delete(message1);
    messageList = messageRepository.findAll();
    Assert.assertEquals(0, messageList.size());
  }
}