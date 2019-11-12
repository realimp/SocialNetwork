package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.BasicPerson;
import ru.skillbox.socialnetwork.api.responses.Feeds;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PostRepository;
import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class FeedsService {

    @Autowired
    PostRepository postRepository;

    public Feeds getFeeds(String query, Integer offset, Integer itemsPerPage) {
        //SortOrder sortOrder = new Sort.Order(Sort.Direction.ASC, "");
        Pageable pageable = PageRequest.of(offset, itemsPerPage);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostResponse> prList = new ArrayList<>();
        postPage.get().forEach(p->{
                BasicPerson basicPerson = new BasicPerson();
                basicPerson.setId(p.getAuthor().getId());
                basicPerson.setFirstName(p.getAuthor().getFirstName());
                basicPerson.setLastName(p.getAuthor().getLastName());
                basicPerson.setPhoto(p.getAuthor().getPhoto());
                Date lastOnlineTime = p.getAuthor().getLastOnlineTime();
                if (lastOnlineTime != null) {
                    basicPerson.setLastOnlineTime(lastOnlineTime.getTime());
                }

                PostResponse postResponse = new PostResponse();
                postResponse.setId(p.getId());
                Date postDate = p.getDate();
                if (postDate != null) {
                    postResponse.setTime(postDate.getTime());
                }
                postResponse.setAuthor(basicPerson);
                postResponse.setTitle(p.getTitle());
                postResponse.setPostText(p.getText());
                postResponse.setBlocked(p.isBlocked());
                //todo likes
                //postResponse.setLikes();
                //todo tags
                //postResponse.setTags();
                //todo myike
                //postResponse.setMyLike();
                //todo comments
                //postResponse.setComments();
                prList.add(postResponse);
        });
        return new Feeds(prList);
    }

}
