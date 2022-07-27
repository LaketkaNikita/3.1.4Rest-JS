package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.entity.User;
import web.repository.UserRepository;
import web.service.UserService;

import java.security.Principal;


@Controller
public class UserController {
    private UserService userService;

    @GetMapping("/api/user")
    @ResponseBody
    public User userPage(User user) {
        return userService.getUserById(user.getId());
    }
}