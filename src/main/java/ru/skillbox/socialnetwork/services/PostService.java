package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.repositories.PostRepository;

import javax.swing.plaf.PanelUI;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post getOnePostById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        //ToDo  вместо null нужно что-то вернуть осмысленное если нет нужного поста.
        return post.orElse(null);
    }

    public Integer deletePostById(int idPost) {
        if (postRepository.findById(idPost).isPresent()) {
            postRepository.deleteById(idPost);
            return idPost;
        }
        return 1; //ToDo тут нужно что то вернуть если поста для удаления нет.
    }

    public List<Post> getPostFromDate(Date time) {
        List<Post> arrayPost = postRepository.findByDateFrom(time);
        if (arrayPost.size() > 0) {
            return arrayPost;
        }
        return null;//ToDo тут нужно что то вернуть если поиск по дате не дал нижных результатов.
    }

    public List<Post> getPostToDate(Date time) {
        List<Post> arrayPost = postRepository.findByDateTo(time);
        if (arrayPost.size() > 0) {
            return arrayPost;
        }
        return null;//ToDo тут нужно что то вернуть если поиск по дате не дал нижных результатов.
    }
}
