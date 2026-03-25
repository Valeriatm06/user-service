package co.edu.uptc.user_service.repository;

import co.edu.uptc.user_service.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);
    List<User> findAll();
}
