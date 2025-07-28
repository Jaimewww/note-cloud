package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.ForumDTO;
import edu.cc.notecloud.entity.Forum;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ForumRepository {

    @Inject
    private UserRepository userRepository;

    @Inject
    private CrudGenericService crudService;

    public List<Forum> findAll() {
        return crudService.findWithQuery("SELECT f FROM Forum f", new HashMap<>());
    }

    public Forum findById(Long id) {
        return crudService.find(Forum.class, id);
    }

    public Forum save(Forum forum) {
        if (forum.getId() == null) {
            return crudService.create(forum);
        } else {
            return crudService.update(forum);
        }
    }

    public void createForum(ForumDTO dto) {
        Forum forum = new Forum();
        forum.setTitle(dto.getTitle());
        forum.setContent(dto.getContent());
        forum.setCreatedAt(LocalDateTime.now());
        forum.setState(true);
        forum.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        forum.setCommentsCount(0); // Inicialmente cero comentarios
        save(forum);
    }
}