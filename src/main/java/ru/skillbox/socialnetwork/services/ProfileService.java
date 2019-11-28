/** @Author Savva */
package ru.skillbox.socialnetwork.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.requests.EditPerson;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.PersonResponse;
import ru.skillbox.socialnetwork.api.responses.PersonsWallPost;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.Tag;
import ru.skillbox.socialnetwork.mappers.PersonMapper;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${com.cloudinary.cloud_name}")
    private String cloudName;
    @Value("${com.cloudinari.url}")
    private String cloudUri;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    public PersonResponse getPerson() {
        Person person = accountService.getCurrentUser();
        if (person != null) {
            logger.info("current user is obtained: {}", person.getId());
        } else {
            logger.warn("could not obtain current user");
        }
        return PersonMapper.getMapping(person);
    }

    public PersonResponse editPerson(EditPerson editPerson) {
        Person person = accountService.getCurrentUser();
        person.setFirstName(editPerson.getFirstName());
        person.setLastName(editPerson.getLastName());
        person.setBirthDate(editPerson.getBirthDate());
        String phoneToSave = editPerson.getPhone();
        if (person.getPhone().length() != phoneToSave.length() + 1 && !person.getPhone().contains(phoneToSave)) {
            person.setPhone(phoneToSave);
        }
        if (editPerson.getPhotoId() != null) {
            person.setPhoto(cloudUri + cloudName + "/image/upload/" + editPerson.getPhotoId());
        }
        person.setAbout(editPerson.getAbout());
        person.setCity(editPerson.getCity());
        person.setCountry(editPerson.getCountry());
        person.setMessagesPermission(editPerson.getMessagesPermission());
        Person savedPerson = personRepository.saveAndFlush(person);

        return PersonMapper.getMapping(savedPerson);
    }

    public MessageResponse deletePerson() {
        Person person = accountService.getCurrentUser();
        if (person != null) {
            logger.info("current user is obtained: {}", person.getId());
        } else {
            logger.warn("could not obtain current user");
        }
        person.setDeleted(true);
        personRepository.saveAndFlush(person);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Current user has been successfully deleted!");

        return messageResponse;
    }

    public PersonResponse getPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        return PersonMapper.getMapping(person);
    }

    public List<PostResponse> getWallPostsById(Integer id, Integer offset, Integer itemPerPage) {
        Pageable pageable = PageRequest.of(offset, itemPerPage);
        Page<Post> postPageList = postRepository.findByAuthor(personRepository.getOne(id), pageable);

        List<Post> postList = postPageList.getContent();
        List<PostResponse> postResponseList = new ArrayList<>();

        for (Post post : postList) {
            postResponseList.add(PostMapper.getPostResponse(post));
        }
        return postResponseList;
    }

    public PostResponse addWallPostById(Integer id, Date publishDate, String title, String post_text, List<Tag> tags) {
        Post post = new Post();

        post.setAuthor(personRepository.getOne(id));
        post.setDate(publishDate);
        post.setTitle(title);
        post.setText(post_text);
        post.setBlocked(false);
        post.setDeleted(false);

        //TODO: update after adding Tag repository

        postRepository.save(post);
        return PostMapper.getPostResponse(post);
    }

    public List<PersonResponse> searchPerson(String firstName, String lastName, Integer ageFrom, Integer ageTo,
                                          String country, String city, Integer offset, Integer itemPerPage) {


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -ageTo);
        Date birthDateFrom = calendar.getTime();
        calendar.add(Calendar.YEAR, ageTo - ageFrom);
        Date birthDateTo = calendar.getTime();
        Pageable pageable = PageRequest.of(offset, itemPerPage);

        //Разбор строки, если в параметрах запроса только один многосложный параметр - firstName
        firstName = firstName.trim().replaceAll("( )+"," ");
        String[] names = firstName.split(" ");
        String searchFirstName = names[0];
        String searchLastName = names[1];

        int firstNameLen = searchFirstName.length();
        int lastNameLen = 0;

        //если в параметрах запроса присутствует второй параметр - lastName то его содержимое будет участвовать в запросе поиска
        if (lastName.trim().length()>0) {
            lastNameLen = lastName.trim().length();
            searchLastName = lastName;
        } else {
            lastNameLen = searchLastName.length();
        }
        int countryLen = country.trim().length();
        int cityLen = city.trim().length();

        Page<Person> personPageList = null;
        if ((firstNameLen > 0) && (lastNameLen == 0) && (countryLen == 0)  && (cityLen == 0)){
            personPageList = personRepository.findByFirstName(searchFirstName, pageable);

        } else if ((firstNameLen > 0) && (lastNameLen > 0) && (countryLen == 0)  && (cityLen == 0)){
            personPageList = personRepository.findByFirstNameAndLastName(searchFirstName, searchLastName, pageable);
        } else if ((firstNameLen > 0) && (lastNameLen > 0) && (countryLen > 0)  && (cityLen == 0)){
            personPageList = personRepository.findByFirstNameAndLastNameAndCountry(searchFirstName, searchLastName, country, pageable);
        } else if ((firstNameLen > 0) && (lastNameLen > 0) && (countryLen > 0)  && (cityLen > 0)){
            personPageList = personRepository.findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween(
                    searchFirstName, searchLastName, country, city, birthDateFrom, birthDateTo, pageable);
        }

        List<Person> personList = personPageList.getContent();
        List<PersonResponse> personResponseList = new ArrayList<>();

        for (Person person : personList) {
            personResponseList.add(PersonMapper.getMapping(person));
        }

        return personResponseList;
    }

    public MessageResponse blockPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        person.setBlocked(true);
        personRepository.saveAndFlush(person);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User with id: "+id+" has been successfully blocked!");
        return messageResponse;
    }

    public MessageResponse unblockPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        person.setBlocked(false);
        personRepository.saveAndFlush(person);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User with id: "+id+" has been successfully unblocked!");
        return messageResponse;
    }
}
