package ru.skillbox.socialnetwork.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.skillbox.socialnetwork.api.requests.CommentRequest;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.PostComment;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.repositories.PostCommentRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PersonRepository personRepository;

    private int testCommentId = 0;

    @Before
    public void setUp() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentText("comment");
        testCommentId = postService.createPostComment(1, commentRequest).getData().getId();
    }

    @WithMockUser(username = "paveldobro92@mail.ru")
    @Test
    public void getOnePostByIdTest() {
        PostResponse postResponse = postService.getOnePostById(1).getData();
        Assert.assertEquals("First post ever!", postResponse.getTitle());
    }

    @WithMockUser(username = "paveldobro92@mail.ru")
    @Test
    public void deletePostByIdTest() {
        IdResponse idResponse = postService.deletePostById(1).getData();
        Assert.assertEquals(Integer.valueOf(1), idResponse.getId());
        Assert.assertTrue(postRepository.findById(1).get().isDeleted());
    }

    @WithMockUser(username = "paveldobro92@mail.ru")
    @Test
    public void recoveryPostByIdTest() {
        postService.deletePostById(1);
        PostResponse postResponse = postService.recoveryPostById(1).getData();
        Assert.assertFalse(postRepository.findById(1).get().isDeleted());
        Assert.assertEquals("First post ever!", postResponse.getTitle());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void addPostTest() {
        CreatePostRequest postRequest = new CreatePostRequest();
        postRequest.setTitle("Title");
        postRequest.setPostText("Text");
        PostResponse postResponse = postService.addPost(new Date(), postRequest).getData();
        Assert.assertEquals(Integer.valueOf(4), postResponse.getAuthor().getId());
        Assert.assertEquals("Title", postResponse.getTitle());
        Assert.assertEquals("Text", postResponse.getPostText());
    }

    @WithMockUser(username = "paveldobro92@mail.ru")
    @Test
    public void postEditingTest() {
        CreatePostRequest postRequest = new CreatePostRequest();
        postRequest.setTitle("First post ever");
        postRequest.setPostText("Welcome everybody! Our social network has opened!");
        PostResponse postResponse = postService.postEditing(1, postRequest).getData();
        Assert.assertEquals(Integer.valueOf(1), postResponse.getAuthor().getId());
        Assert.assertEquals("First post ever", postResponse.getTitle());
        Assert.assertEquals("Welcome everybody! Our social network has opened!", postResponse.getPostText());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void createPostCommentTest() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentText("comment");
        Comment comment = postService.createPostComment(1, commentRequest).getData();
        testCommentId = comment.getId();
        Assert.assertEquals(Integer.valueOf(4), comment.getAuthor().getId());
        Assert.assertEquals("1", comment.getPostId());
        Assert.assertEquals("comment", comment.getCommentText());
        Assert.assertTrue(comment.getId() != null);
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void savePostCommentTest() {
        PostComment comment = postCommentRepository.getOne(testCommentId);
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentText("comment that is");
        Comment commentResponse = postService.savePostComment(1, comment.getId(), commentRequest).getData();
        Assert.assertEquals("comment that is", commentResponse.getCommentText());
        Assert.assertEquals(Integer.valueOf(testCommentId), commentResponse.getId());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void getCommentsTest() {
        List<Comment> commentList = postService.getComments(1, PageRequest.of(0, 20)).getData();
        Assert.assertTrue(commentList.size() > 0);
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void deletePostCommentTest() {
        IdResponse idResponse = postService.deletePostComment(1, testCommentId).getData();
        Assert.assertEquals(Integer.valueOf(testCommentId), idResponse.getId());
        Assert.assertTrue(postCommentRepository.findById(testCommentId).get().isDeleted());
    }

    @WithMockUser(username = "test@email.com")
    @Test
    public void recoveryPostCommentTest() {
        postService.recoveryPostComment(1, testCommentId).getData();
        Assert.assertFalse(postCommentRepository.findById(testCommentId).get().isDeleted());
    }

    @After
    public void cleanUp() {
        Post post = postRepository.findById(1).get();
        post.setDeleted(false);
        post.setTitle("First post ever!");
        postRepository.saveAndFlush(post);
        Page<Post> byAuthorId = postRepository.findByAuthorId(4, PageRequest.of(0, 1));
        if (byAuthorId.hasContent()) {
            Post postToDelete = byAuthorId.getContent().get(0);
            postRepository.delete(postToDelete);
        }

        postCommentRepository.delete(postCommentRepository.findById(testCommentId).get());

        List<Comment> commentList = postService.getComments(1, PageRequest.of(0, 40)).getData();
        if (commentList.size() > 0) {
            for (Comment comment : commentList) {
                if (comment.getCommentText().equalsIgnoreCase("comment")
                        || comment.getCommentText().equalsIgnoreCase("comment that is")) {
                    postCommentRepository.delete(postCommentRepository.findById(comment.getId()).get());
                }
            }
        }
    }
}
