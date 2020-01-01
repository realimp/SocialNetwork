package ru.skillbox.socialnetwork.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Message;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void findByDialogIdAndMessageTextTest() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Message> page = messageRepository.findByDialogIdAndMessageText(1, "Принимай заявку в друзья!", pageable);
        Assert.assertEquals(1, page.getTotalElements());
    }

    @Test
    public void findByDialogIdTest() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Message> page = messageRepository.findByDialogId(1, pageable);
        Assert.assertEquals(1, page.getTotalElements());
    }
}