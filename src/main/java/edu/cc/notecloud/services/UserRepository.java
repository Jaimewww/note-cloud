package edu.cc.notecloud.services;

import edu.cc.notecloud.entity.User;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Stateless
public class UserRepository {

    @Inject
    CrudGenericService crudService;

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(crudService.find(User.class, id));
    }

    public Optional<User> findByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<User> result = crudService.findWithQuery(jpql, params);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public User save(User user) {
        if (user.getId() == null) {
            return crudService.create(user);
        } else {
            return crudService.update(user);
        }
    }
}