package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.responses.BasicPerson;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;
import java.util.Date;

public class PostMapper {

    public static PostResponse getPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        Person person = post.getAuthor();
        BasicPerson basicPerson = new BasicPerson();
        if (person != null) {
            basicPerson.setId(person.getId());
            basicPerson.setFirstName(person.getFirstName());
            basicPerson.setLastName(person.getLastName());
            basicPerson.setPhoto(person.getPhoto());
            Date lastOnlineTime = post.getAuthor().getLastOnlineTime();
            if (lastOnlineTime != null) {
                basicPerson.setLastOnlineTime(lastOnlineTime.getTime());
            }
        }
        postResponse.setId(post.getId());
        Date postDate = post.getDate();
        if (postDate != null) {
            postResponse.setTime(postDate.getTime());
        } else {
            postResponse.setTime(new Date().getTime());
        }
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
