package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.Feeds;
import ru.skillbox.socialnetwork.api.responses.PostResponse;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.PostComment;
import ru.skillbox.socialnetwork.entities.PostLike;
import ru.skillbox.socialnetwork.mappers.PostCommentMapper;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.repositories.PostCommentRepository;
import ru.skillbox.socialnetwork.repositories.PostLikeRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private PostCommentRepository postCommentRepository;

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
            List<PostComment> postcomments = postCommentRepository.findByPostId(post.getId());
            Map<Integer, List<PostComment>> childComments = postcomments.stream()
                    .collect(Collectors.toMap(PostComment::getId, comment ->
                            postCommentRepository.findByPostIdByParentId(post.getId(), comment.getId())
                    ));
            postResponse.setComments(PostCommentMapper.getRootComments(postcomments, childComments));
            prList.add(postResponse);
        });
        return new Feeds(prList);
    }

}
