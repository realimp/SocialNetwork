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
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
import ru.skillbox.socialnetwork.repositories.PostCommentRepository;
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
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;

    public Feeds getFeeds(String query, Integer offset, Integer itemsPerPage) {

        Pageable pageable = PageRequest.of(offset, itemsPerPage);
//        Sort sort = new Sort(Sort.Direction.DESC, "date");
//        Pageable pageable = PageRequest.of(offset, itemsPerPage, sort);
//        Page<Post> postPage = postRepository.findAll(pageable);
        Person me = accountService.getCurrentUser();
        List<Friendship> friends = friendshipRepository.findByFriends(me, FriendshipStatus.FRIEND);
        List<Integer> friendsId  = friends.stream().map((f) -> f.getSrcPerson().getId()).collect(Collectors.toList());
        friendsId.add(me.getId());
        Page <Post> pagePosts = postRepository.findByManyAuthors(friendsId, pageable);
        List<PostResponse> postsList = new ArrayList<>();
        pagePosts.forEach((p -> {
            postsList.add(getPostResponseFromPost(p, me));
        }));
        //        postRepository.findByAuthor(me, pageable);
        //todo Доделать метод у репозитория

        //postRepository.findByQuery(query);
//        List<PostResponse> prList = new ArrayList<>();
//        postPage.get().forEach(post-> {
//            PostResponse postResponse = PostMapper.getPostResponse(post);
//            List<PostLike> likes = postLikeRepository.findByPostId(post.getId());
//            int likesCount = 0;
//            if (likes != null) {
//                likesCount = likes.size();
//            }
//            postResponse.setLikes(likesCount);
//            boolean myLike = false;
//            List<PostLike> myLikes = likes.stream()
//                    .filter(postLike -> postLike.getPerson().equals(accountService.getCurrentUser()))
//                    .collect(Collectors.toList());
//            if (myLikes.size() >= 1) {
//                myLike = true;
//            }
//            postResponse.setMyLike(myLike);
//            //ToDo Add post tags
//            postResponse.setTags(new ArrayList<String>(){{
//                add("Backend hasn't have entity for post tag");
//            }});
//            //ToDo Add post comments
//            postResponse.setComments(new ArrayList<Comment>(){{add(new Comment());}});
//            prList.add(postResponse);
//        });
        return new Feeds(postsList);//new Feeds(prList);

    }

    private PostResponse getPostResponseFromPost(Post p, Person me){
        PostResponse postResponse = PostMapper.getPostResponse(p);
        List<PostLike> likes = postLikeRepository.findByPostId(postResponse.getId());
        int likeCount = likes != null ? likes.size() : 0;
        boolean myLike = likes.stream().filter(like -> like.getPerson().equals(me)).
                collect(Collectors.toList()).size() == 1;

        postResponse.setMyLike(myLike);
        postResponse.setLikes(likeCount);
        postResponse.setComments(getCommentByPost(p));
        postResponse.setTags(getTagsByPost(p));
        return postResponse;
    }

    private List<String> getTagsByPost(Post p) {
        List<String> tags = new ArrayList<>();
        //TODO find tags
        return tags;
    }

    private List<Comment> getCommentByPost(Post post){
        List<PostComment> comments = postCommentRepository.findByPostId(post.getId());
        //TODO map PostComment to Comment
        List <Comment> commentResponse = new ArrayList<>();
        return commentResponse;
    }

}
