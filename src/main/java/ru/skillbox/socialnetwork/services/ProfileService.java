/**
 * @Author Savva
 */
package ru.skillbox.socialnetwork.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.api.requests.EditPerson;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.mappers.PersonMapper;
import ru.skillbox.socialnetwork.mappers.PostCommentMapper;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.repositories.*;

import java.util.*;

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

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

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

    public List<PersonsWallPost> getWallPostsById(Integer id, Integer offset, Integer itemPerPage) {
        Pageable pageable = PageRequest.of(offset, itemPerPage);
        Person author = personRepository.findById(id).get();
        Page<Post> postPageList = postRepository.findByAuthorId(author.getId(), pageable);

        List<PersonsWallPost> postResponseList = new ArrayList<>();

        if (!postPageList.isEmpty()) {
            for (Post post : postPageList) {
                PersonsWallPost wallPost = new PersonsWallPost();
                wallPost.setId(post.getId());
                wallPost.setTime(post.getDate().getTime());
                PersonResponse postAuthor = PersonMapper.getMapping(author);
                wallPost.setAuthor(postAuthor);
                wallPost.setTitle(post.getTitle());
                wallPost.setPostText(post.getText());
                wallPost.setBlocked(post.isBlocked());
                wallPost.setLikes(postLikeRepository.findByPostId(post.getId()).size());
                wallPost.setTags(getTagsByPost(post));
                List<PostComment> postcomments = postCommentRepository.findByPostId(post.getId());
                wallPost.setComments(PostCommentMapper.getRootComments(postcomments,
                        postService.getChildComments(post.getId(), postcomments)));
                if (post.getDate().before(new Date())) {
                    wallPost.setType(PostType.POSTED);
                } else {
                    wallPost.setType(PostType.QUEUED);
                }

                postResponseList.add(wallPost);
            }
        }
        return postResponseList;
    }

    public PostResponse addWallPostById(Integer id, Date publishDate, String title, String post_text, List<Tag> tags) {
        Post post = new Post();
        List<Tag> postTags = new ArrayList<>();
        post.setAuthor(personRepository.getOne(id));
        post.setDate(publishDate);
        post.setTitle(title);
        post.setText(post_text);
        post.setBlocked(false);
        post.setDeleted(false);
        for (Tag tag : tags) {
            postTags.add(tagService.saveTag(tag));
        }
        post.setTags(postTags);

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

        HashMap<String, String> namesToSearch = new HashMap<>();
        int firstNameLen = firstName.trim().length();
        int lastNameLen = lastName.trim().length();
        //Разбор строки, если в параметрах запроса только один составной параметр - firstName
        if ((firstNameLen > 0) && (lastNameLen == 0)) {
            namesToSearch = obtainStringsForSearch(firstName);
        }
        int countryLen = country.trim().length();
        int cityLen = city.trim().length();

        List<Person> personList = new ArrayList<Person>();
        if ((firstNameLen > 0) && (lastNameLen == 0) && (countryLen == 0) && (cityLen == 0)) {
            if (namesToSearch != null) {
                if (!namesToSearch.get("lastName").equals("")) {
                    Page<Person> personPageList = personRepository.findByFirstNameAndLastName(namesToSearch.get("firstName"), namesToSearch.get("lastName"), pageable);
                    personList.addAll(personPageList.getContent());
                    personPageList = personRepository.findByFirstNameAndLastName(namesToSearch.get("lastName"), namesToSearch.get("firstName"), pageable);
                    personList.addAll(personPageList.getContent());
                } else {
                    Page<Person> personPageList = personRepository.findByFirstName(namesToSearch.get("firstName"), pageable);
                    personList.addAll(personPageList.getContent());
                    personPageList = personRepository.findByLastName(namesToSearch.get("firstName"), pageable);
                    personList.addAll(personPageList.getContent());
                }
            }
        } else if ((firstNameLen > 0) && (lastNameLen > 0) && (countryLen == 0) && (cityLen == 0)) {
            Page<Person> personPageList = personRepository.findByFirstNameAndLastName(firstName.trim(), lastName.trim(), pageable);
            personList.addAll(personPageList.getContent());
        } else if ((firstNameLen > 0) && (lastNameLen > 0) && (countryLen > 0) && (cityLen == 0)) {
            Page<Person> personPageList = personRepository.findByFirstNameAndLastNameAndCountry(firstName, lastName, country, pageable);
            personList.addAll(personPageList.getContent());
        } else if ((firstNameLen > 0) && (lastNameLen > 0) && (countryLen > 0) && (cityLen > 0)) {
            Page<Person> personPageList = personRepository.findByFirstNameAndLastNameAndCountryAndCityAndBirthDateBetween(
                    firstName, lastName, country, city, birthDateFrom, birthDateTo, pageable);
            personList.addAll(personPageList.getContent());
        }

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
        messageResponse.setMessage("User with id: " + id + " has been successfully blocked!");
        return messageResponse;
    }

    public MessageResponse unblockPersonById(Integer id) {
        Person person = personRepository.getOne(id);
        person.setBlocked(false);
        personRepository.saveAndFlush(person);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User with id: " + id + " has been successfully unblocked!");
        return messageResponse;
    }

    private HashMap<String, String> obtainStringsForSearch(String pString) {
        HashMap searchMap = new HashMap();
        pString = pString.trim().replaceAll("( )+", " ");
        String[] names = pString.split(" ");
        String searchFirstName = names[0];
        String searchLastName = "";
        if (names.length > 1) {
            searchLastName = names[1].trim();
        }
        if (searchFirstName.length() > 0) {
            searchMap.put("firstName", searchFirstName);
        } else {
            searchMap.put("firstName", "");
        }
        if (searchLastName.length() > 0) {
            searchMap.put("lastName", searchLastName);
        } else {
            searchMap.put("lastName", "");
        }
        return searchMap;

    }

    private List<String> getTagsByPost(Post p) {
        List<String> tags = new ArrayList<>();
        for (Tag tag : p.getTags()) {
            tags.add(tag.getText());
        }
        return tags;
    }

}
