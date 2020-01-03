package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.requests.CommentRequest;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.*;
import ru.skillbox.socialnetwork.mappers.PostCommentMapper;
import ru.skillbox.socialnetwork.mappers.PostMapper;
import ru.skillbox.socialnetwork.repositories.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;

    public Response<PostResponse> getOnePostById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        return new Response<>(PostMapper.getPostResponse(post.get()));
    }

    public Response<IdResponse> deletePostById(int idPost) {
        Post post = postRepository.findById(idPost).get();
        post.setDeleted(true);
        postRepository.saveAndFlush(post);
        IdResponse responseData = new IdResponse(post.getId());
        return new Response<>(responseData);
    }

    public Response<PostResponse> recoveryPostById(int idPost) {
        Post post = postRepository.findById(idPost).get();
        post.setDeleted(false);
        return new Response<>(PostMapper.getPostResponse(postRepository.saveAndFlush(post)));
    }

    public Response<PostResponse> addPost(Date publishDate, CreatePostRequest postRequest) {
        Post newPost = new Post();
        newPost.setDate(publishDate);
        newPost.setTitle(postRequest.getTitle());
        newPost.setText(postRequest.getPostText());
        newPost.setBlocked(false);
        newPost.setDeleted(false);
        newPost.setAuthor(accountService.getCurrentUser());

        return new Response<>(PostMapper.getPostResponse(postRepository.saveAndFlush(newPost)));
    }

    public Response<PostResponse> postEditing(Integer postId, CreatePostRequest createPostRequest) {
        Post post = postRepository.findById(postId).get();
        if (accountService.getCurrentUser().getId() == post.getAuthor().getId()) {
            post.setTitle(createPostRequest.getTitle());
            post.setText(createPostRequest.getPostText());
            return new Response<>(PostMapper.getPostResponse(postRepository.save(post)));
        }
        return null;
    }

    public Response<Comment> createPostComment(int id, CommentRequest commentRequest) {
        Person commentAuthor = accountService.getCurrentUser();
        Post post = postRepository.getOne(id);
        Person postAuthor = post.getAuthor();
        PostComment comment = new PostComment();
        comment.setAuthor(commentAuthor);
        comment.setDeleted(false);
        comment.setDate(new Date());
        comment.setBlocked(false);
        comment.setCommentText(commentRequest.getCommentText());
        comment.setPost(post);

        for (NotificationSettings setting : notificationSettingsRepository.findByPersonId(postAuthor)) {
            if (setting.getNotificationTypeCode().name().equalsIgnoreCase("POST_COMMENT")) {
                Notification notification = new Notification(
                        NotificationTypeCode.POST_COMMENT, new Date(), commentAuthor, postAuthor, id, postAuthor.getEMail());
                notificationRepository.saveAndFlush(notification);
            }
        }
        return new Response<>(PostCommentMapper.getComment(postCommentRepository.saveAndFlush(comment)));
    }

    public Response<Comment> savePostComment(int id, Integer comment_id, CommentRequest commentRequest) {
        PostComment postComment = postCommentRepository.getOne(comment_id);
        if (postComment.getAuthor() == accountService.getCurrentUser()) {
            postComment.setCommentText(commentRequest.getCommentText());
            postCommentRepository.saveAndFlush(postComment);

            Post post = postRepository.getOne(id);
            Person postAuthor = post.getAuthor();
            Person commentAuthor = accountService.getCurrentUser();

            for (NotificationSettings setting : notificationSettingsRepository.findByPersonId(postAuthor)) {
                if (setting.getNotificationTypeCode().name().equalsIgnoreCase("POST_COMMENT")) {
                    Notification notification = new Notification(
                            NotificationTypeCode.POST_COMMENT, new Date(), commentAuthor, postAuthor, id, postAuthor.getEMail());
                    notificationRepository.saveAndFlush(notification);
                }
            }
        }
        return new Response<>(PostCommentMapper.getComment(postComment));
    }

    public ResponseList<List<Comment>> getComments(int id, Pageable pageable) {
        Page<PostComment> postComments = postCommentRepository.findByPostId(id, pageable);
        List<Comment> comments = new ArrayList<>();
        long total = 0;
        if (postComments.hasContent()) {
            postComments.forEach(c -> comments.add(PostCommentMapper.getComment(c)));
            total = postComments.getTotalElements();
        }
        return new ResponseList<>(comments, total);
    }

    public Response<IdResponse> deletePostComment(int id, int comment_id) {
        PostComment postComment = postCommentRepository.getOne(comment_id);
        if (postComment.getPost().getId() != id)
            return new Response<>("Идентификатор поста не соответствует идентификатору поста комментария", null);
        postComment.setDeleted(true);
        postCommentRepository.saveAndFlush(postComment);
        return new Response<>(new IdResponse(postComment.getId()));
    }

    public Response<List<Comment>> recoveryPostComment(int id, int comment_id) {
        Optional<PostComment> optionalPostComment = postCommentRepository.findById(comment_id);
        if (!optionalPostComment.isPresent())
            return new Response<>("Не найден комментарий с идентификатором " + comment_id, null);
        PostComment postComment = optionalPostComment.get();
        postComment.setDeleted(false);
        postCommentRepository.saveAndFlush(postComment);
        return getComments(id, PageRequest.of(0, 20));
    }

    public Map<Integer, List<PostComment>> getChildComments(Integer postId, List<PostComment> postcomments) {
        return postcomments.stream()
                .collect(Collectors.toMap(PostComment::getId, comment ->
                        postCommentRepository.findByPostIdByParentId(postId, comment.getId())
                ));
    }

    public ResponseList postSearch(String query, Pageable pageable) {
        ArrayList<PostResponse> responseData = new ArrayList<>();
        if (tagRepository.existsByTag(query)) {
            Page<Post> posts = postRepository.findByTags(tagRepository.findByTag(query), pageable);
            if (posts.hasContent()) {
                for (Post post : posts) {
                    responseData.add(PostMapper.getPostResponse(post));
                }
            }
            ResponseList responseList = new ResponseList(responseData);
            responseList.setTotal(posts.getTotalElements());
            responseList.setOffset(pageable.getOffset());
            responseList.setPerPage(pageable.getPageSize());

            return responseList;
        }
        return new ResponseList();
    }

    public Response<MessageResponse> reportPost(int id) {
        Post post = postRepository.findById(id).get();
        post.setBlocked(true);
        postRepository.saveAndFlush(post);
        return new Response<>(new MessageResponse("ok"));
    }

    public Response<MessageResponse> reportComment(int postId, int commentId) {
        PostComment comment = postCommentRepository.findById(commentId).get();
        comment.setBlocked(true);
        postCommentRepository.saveAndFlush(comment);
        return new Response<>(new MessageResponse("ok"));
    }
}
