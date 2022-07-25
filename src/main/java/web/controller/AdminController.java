package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.entity.Role;
import web.service.RoleService;
import web.service.UserService;

@Controller
public class AdminController {
    private final RoleService roleService;

    public AdminController(RoleService roleService) {
        this.roleService = roleService;
        if (roleService.getAllRoles().isEmpty()) {
            roleService.save(new Role("ADMIN"));
            roleService.save(new Role("USER"));
        }
    }

    @GetMapping
    public String indexView() {
        return "index";
    }

    @GetMapping("/admin")
    public String userView() {
        return "admin";
    }
}
