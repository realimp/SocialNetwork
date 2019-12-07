package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.requests.CommentRequest;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.requests.PostRequest;
import ru.skillbox.socialnetwork.api.responses.Comment;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.PostComment;
import ru.skillbox.socialnetwork.mappers.PostCommentMapper;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.repositories.PostCommentRepository;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Post getOnePostById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        //ToDo  вместо null нужно что-то вернуть осмысленное если нет нужного поста.
        return post.orElse(null);
    }

    public Integer deletePostById(int idPost) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            post.get().setDeleted(true);
            postRepository.save(post.get());
            return idPost;
        }
        return 1; //ToDo тут нужно что то вернуть если поста для удаления нет.
    }

    public Integer recoveryPostById(int idPost) {
        Optional<Post> post = postRepository.findById(idPost);
        if (post.isPresent()) {
            post.get().setDeleted(false);
            postRepository.save(post.get());
            return idPost;
        }
        return 1; //ToDo тут нужно что то вернуть если поста для восстановления нет.
    }

    public String addPost(int idAuthor, Date publishDate, PostRequest postRequest) {
        Post newPost = new Post();
        newPost.setDate(publishDate);
        newPost.setTitle(postRequest.getTitle());
        newPost.setText(postRequest.getPostText());
        newPost.setBlocked(false);
        newPost.setDeleted(false);
        newPost.setAuthor(accountService.getCurrentUser());

        postRepository.save(newPost);

        return null;
    }

    public Post postEditing(Integer postId, CreatePostRequest createPostRequest) {

        Optional<Post> post = postRepository.findById(postId);
        if (accountService.getCurrentUser().getId() == post.get().getAuthor().getId()) {
            post.get().setTitle(createPostRequest.getTitle());
            post.get().setText(createPostRequest.getPostText());
            postRepository.save(post.get());
        }
        return post.get();
    }

    public Response<Comment> savePostComment(int id, Integer comment_id, CommentRequest commentRequest) {
        PostComment postComment = new PostComment();
        if (commentRequest.getAuthor() != null) {
            Optional<Person> personOptional = personRepository.findById(commentRequest.getAuthor().getId());
            if (!personOptional.isPresent())
                return new Response<>("Не найден пользователь с идентификатором " + commentRequest.getAuthor().getId(), null);
            postComment.setAuthor(personOptional.get());
        } else postComment.setAuthor(accountService.getCurrentUser());
        postComment.setBlocked(commentRequest.isBlocked());
        postComment.setCommentText(commentRequest.getCommentText());
        if (commentRequest.getTime() == null)
            postComment.setDate(new Date());
        else
            postComment.setDate(commentRequest.getTime());
        if (comment_id != null)
            postComment.setId(comment_id);
        if (commentRequest.getParentId() != null) {
            Optional<PostComment> parentPostComment = postCommentRepository.findById(commentRequest.getParentId());
            if (!parentPostComment.isPresent())
                return new Response<>("Не найден комментарий с идентификатором " + id, null);
            postComment.setParentComment(parentPostComment.get());
        }
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent())
            return new Response<>("Не найден пост с идентификатором " + id, null);
        postComment.setPost(postOptional.get());
        postComment.setDeleted(false);
        postCommentRepository.saveAndFlush(postComment);
        return new Response<>(PostCommentMapper.getComment(postComment));
    }

    public ResponseList<List<Comment>> getComments(int id) {
        List<PostComment> postComments = postCommentRepository.findByPostId(id);
        List<Comment> comments = new ArrayList<>();
        postComments.forEach(c-> comments.add(PostCommentMapper.getComment(c)));
        return new ResponseList<>(comments, comments.size());
    }
}
