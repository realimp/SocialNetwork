package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post getOnePostById(Integer id){
        Optional<Post> post = postRepository.findById(id);
        //ToDo  вместо null нужно что-то вернуть осмысленное если нет нужного поста.
        return post.orElse(null);
    }
}
