package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.Comment;
import ru.skillbox.socialnetwork.api.responses.Feeds;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.PostLike;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.repositories.PostLikeRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedsService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private AccountService accountService;

    public Feeds getFeeds(String query, Integer offset, Integer itemsPerPage) {
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(offset, itemsPerPage, sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        //todo Доделать метод у репозитория
        //postRepository.findByQuery(query);
        List<PostResponse> prList = new ArrayList<>();
        postPage.get().forEach(post-> {
            PostResponse postResponse = PostMapper.getPostResponse(post);
            List<PostLike> likes = postLikeRepository.findByPostId(post.getId());
            int likesCount = 0;
            if (likes != null) {
                likesCount = likes.size();
            }
            postResponse.setLikes(likesCount);
            boolean myLike = false;
            List<PostLike> myLikes = likes.stream()
                    .filter(postLike -> postLike.getPerson().equals(accountService.getCurrentUser()))
                    .collect(Collectors.toList());
            if (myLikes.size() >= 1) {
                myLike = true;
            }
            postResponse.setMyLike(myLike);
            //ToDo Add post tags
            postResponse.setTags(new ArrayList<String>(){{
                add("Backend hasn't have entity for post tag");
            }});
            //ToDo Add post comments
            postResponse.setComments(new ArrayList<Comment>(){{add(new Comment());}});
            prList.add(postResponse);
        });
        return new Feeds(prList);
    }

}
