package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.responses.BasicPerson;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;

public class PostMapper {

    public static PostResponse getPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        BasicPerson basicPerson = new BasicPerson();

        Person person = new Person();
        //как нам тут получить нужного персонажа, именно автора поста???

        basicPerson.setId(person.getId());
        basicPerson.setFirstName(person.getFirstName());
        basicPerson.setLastName(person.getLastName());
        basicPerson.setPhoto(person.getPhoto());
        basicPerson.setLastOnlineTime(person.getLastOnlineTime().getTime());


        postResponse.setId(post.getId());
        postResponse.setTime(post.getDate().getTime());
        postResponse.setAuthor(basicPerson);
        postResponse.setTitle(post.getTitle());
        postResponse.setPostText(post.getText());
        postResponse.setBlocked(post.isBlocked());
        //где мы берем инфу для остальных полей -
        //private Integer likes;
        //private List<String> tags;
        //private Boolean myLike;
        //private List<Comment> comments;


        return postResponse;
    }
}
