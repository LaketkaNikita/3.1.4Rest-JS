package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.entity.User;
import web.service.UserService;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;


@Controller
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

    @GetMapping("/api/user")
    @ResponseBody
    public ResponseEntity<User> userPage(Principal user) {
        return new ResponseEntity<>(userService.findByLogin(user.getName()).get(), HttpStatus.OK);
    }
}