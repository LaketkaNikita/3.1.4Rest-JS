package web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.entity.Role;
import web.entity.User;
import web.service.FileConverterService;
import web.service.RoleService;
import web.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;
    private final FileConverterService fileConverterService;
    @Autowired
    public AdminRestController(UserService userService, RoleService roleService,
                               FileConverterService fileConverterService) {
        this.userService = userService;
        this.roleService = roleService;
        this.fileConverterService = fileConverterService;
    }
    @GetMapping("/users")
    public Set<User> getUsers() {
        return userService.getAllUser();
    }
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }
    @PostMapping("/users")
    public User createNewUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }
    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}