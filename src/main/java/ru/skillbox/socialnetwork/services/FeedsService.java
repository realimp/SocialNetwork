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
import ru.skillbox.socialnetwork.api.responses.TagResponse;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.PostComment;
import ru.skillbox.socialnetwork.entities.PostLike;
import ru.skillbox.socialnetwork.mappers.PostCommentMapper;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.repositories.FriendshipRepository;
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
    private FriendshipRepository friendshipRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    public Feeds getFeeds(String query, Integer offset, Integer itemsPerPage) {
        Pageable pageable = PageRequest.of(offset, itemsPerPage);
        Person me = accountService.getCurrentUser();
        List<Friendship> friends = friendshipRepository.findByFriends(me, FriendshipStatus.FRIEND);
        List<Integer> friendsId = friends.stream().map((f) -> f.getSrcPerson().getId()).collect(Collectors.toList());
        friendsId.add(me.getId());
        Page<Post> pagePosts = postRepository.findByManyAuthors(friendsId, pageable);
        List<PostResponse> postsList = new ArrayList<>();
        pagePosts.forEach((p -> postsList.add(getPostResponseFromPost(p, me))));
        return new Feeds(postsList);
    }

    private PostResponse getPostResponseFromPost(Post p, Person me) {
        PostResponse postResponse = PostMapper.getPostResponse(p);
        List<PostLike> likes = postLikeRepository.findByPostId(postResponse.getId());
        int likeCount = likes != null ? likes.size() : 0;
        boolean myLike = likes != null && likes.stream().filter(like -> like.getPerson().equals(me)).count() == 1;

        postResponse.setMyLike(myLike);
        postResponse.setLikes(likeCount);
        postResponse.setComments(getCommentByPost(p));
        postResponse.setTags(getTagsByPost(p));
        return postResponse;
    }

    private List<String> getTagsByPost(Post p) {
        List<String> tags = new ArrayList<>();
        for (Tag tag : p.getTags()) {
            tags.add(tag.getText());
        }
        return tags;
    }

    private List<Comment> getCommentByPost(Post post) {
        List<PostComment> comments = postCommentRepository.findByPostId(post.getId());
        return PostCommentMapper.getRootComments(comments, postService.getChildComments(post.getId(), comments));
    }
}
