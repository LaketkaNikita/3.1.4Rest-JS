package web.service;

import web.entity.Role;
import web.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Set<User> getAllUser();
    Optional<User> findByLogin(String login);
    void createUser(User user);
    void updateUser(User user);
    void delete(long id);

}