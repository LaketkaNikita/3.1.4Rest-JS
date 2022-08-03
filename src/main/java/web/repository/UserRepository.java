package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import web.entity.User;

import java.util.Optional;
import java.util.Set;


public interface UserRepository extends JpaRepository <User, Long> {
    @Query(value = "from User u join fetch u.roles where u.email = ?1")
    Optional<User> findByLogin(String login);

    @Query(value = "from User u join fetch u.roles order by u.id")
    Set<User> getAllUsers();
}