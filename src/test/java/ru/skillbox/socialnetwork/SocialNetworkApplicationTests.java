package ru.skillbox.socialnetwork;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.EMailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SocialNetworkApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private EMailService eMailService;

    @Test
    public void sendEmail() {
        eMailService.sendEMail("test@gmail.com", "test@gmail.com", "Test", "Test mail");
    }

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testAddOnePerson() {
        Person person = new Person();
        person.setFirstName("Oleg");
        person.setLastName("Petrov");
        person.setPhone("9009001010");
        person.setPassword("password");
        person.setApproved(true);
        person.setBlocked(false);
        person.setOnline(true);
        person.setDeleted(false);
        personRepository.save(person);
    }
	@Test
	public void testUpdatePerson() {
		Person person = new Person();
		person.setId(1);
		person.setFirstName("Oleg");
		person.setLastName("Petrov");
		person.setPhone("8881112266");
		person.setPassword("password");
		person.setApproved(true);
		person.setBlocked(false);
		person.setOnline(true);
		person.setDeleted(false);
		personRepository.save(person);
	}
	@Test
	public void testDeletePerson() {
		Person person = new Person();
		person.setId(1);
		personRepository.delete(person);
	}
}
