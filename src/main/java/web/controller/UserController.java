package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.entity.User;
import web.service.UserService;

import java.security.Principal;
import java.util.Optional;


@Controller
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/api/user")
    @ResponseBody
    public Optional<User> userPage(Principal user) {
        return userService.findByLogin(user.getName());
    }
    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
}