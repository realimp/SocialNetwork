package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.requests.CreatePostRequest;
import ru.skillbox.socialnetwork.api.requests.PostRequest;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

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

}
