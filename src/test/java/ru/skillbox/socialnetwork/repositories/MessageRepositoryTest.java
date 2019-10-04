package ru.skillbox.socialnetwork.repositories;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Message;
import ru.skillbox.socialnetwork.entities.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRepositoryTest {

  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private PersonRepository personRepository;

  private Person person1 = new Person();
  private Person person2 = new Person();
  private Person person3 = new Person();
  private Message message1 = new Message();
  private Message message2 = new Message();
  private Message message3 = new Message();
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

    person2.setFirstName("pers2 FirstName");
    person2.setLastName("pers2 LastName");
    person2.setPhone("22222");
    person2.setPassword("2");
    person2.setApproved(true);
    person2.setBlocked(false);
    person2.setDeleted(false);
    person2.setOnline(false);

    person3.setFirstName("pers3 FirstName");
    person3.setLastName("pers3 LastName");
    person3.setPhone("33333");
    person3.setPassword("3");
    person3.setApproved(true);
    person3.setBlocked(false);
    person3.setDeleted(false);
    person3.setOnline(false);

    message1.setAuthorId(1);
    message1.setRecipientId(2);
    message1.setMessageText("messageText1");
    message1.setDialogId(1);
    message1.setDeleted(false);

    message2.setAuthorId(1);
    message2.setRecipientId(2);
    message2.setMessageText("messageText2");
    message2.setDialogId(2);
    message2.setDeleted(false);

    message3.setAuthorId(1);
    message3.setRecipientId(3);
    message3.setMessageText("messageText3");
    message3.setDialogId(3);
    message3.setDeleted(false);

    personRepository.save(person1);
    personRepository.save(person2);
    personRepository.save(person3);

    messageRepository.save(message1);
    messageRepository.save(message2);
    messageRepository.save(message3);

    messageList = messageRepository.findAll();
    Assert.assertEquals(3, messageList.size());

    messageRepository.delete(message1);
    messageList = messageRepository.findAll();
    Assert.assertEquals(2, messageList.size());

    message3.setMessageText("change Text3");
    messageRepository.save(message3);

    //Assert.assertEquals("messageText2", messageRepository.getOne(2).getMessageText());
  }
}