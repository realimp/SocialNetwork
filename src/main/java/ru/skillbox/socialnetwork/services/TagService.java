package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.entities.Post;
import ru.skillbox.socialnetwork.entities.Tag;
//import ru.skillbox.socialnetwork.repositories.TagRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService {
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    public Tag getTagById(Integer id) {
//        Optional<Tag> tag = tagRepository.findById(id);
//        return tag.orElse(null);
//    }
//
//    public List<Tag> getTagByText(String text) {
//        List<Tag> tags = tagRepository.findByText(text);
//        return tags;
//    }

}
