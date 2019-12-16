package ru.skillbox.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.api.responses.TagResponse;
import ru.skillbox.socialnetwork.entities.Tag;
import ru.skillbox.socialnetwork.repositories.TagRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public ResponseList getTags(String tag, Pageable pageable) {
        Page<Tag> results = tagRepository.findByTag(tag, pageable);
        if (results.isEmpty()) {
            return new ResponseList();
        }
        ArrayList<TagResponse> responseData = new ArrayList<>();
        for (Tag result : results) {
            responseData.add(new TagResponse(result.getId(), result.getText()));
        }
        return new ResponseList();
    }

    public Response createTag(String text) {
        if (tagRepository.existsByTag(text)) {
            return new Response();
        }
        Tag tag = new Tag();
        tag.setText(text);
        Tag savedTag = tagRepository.saveAndFlush(tag);
        return new Response(new TagResponse(savedTag.getId(), savedTag.getText()));
    }

    public Response deleteTag(int id) {
        tagRepository.delete(tagRepository.findById(id).get());
        return new Response(new MessageResponse("ok"));
    }

    public Tag saveTag(Tag tag) {
        if (tagRepository.existsByTag(tag.getText())) {
            return tagRepository.findByTag(tag.getText());
        }
        return tagRepository.saveAndFlush(tag);
    }
}
