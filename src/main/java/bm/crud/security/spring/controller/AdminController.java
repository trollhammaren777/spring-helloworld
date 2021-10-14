package bm.crud.security.spring.controller;

import bm.crud.security.spring.model.User;
import bm.crud.security.spring.service.RoleService;
import bm.crud.security.spring.service.UserService;
import bm.crud.security.spring.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping()
    public String helloAdminPage(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, Admin");
        messages.add("Let's do smth");
        model.addAttribute("messagesForAdmin", messages);
        model.addAttribute("user", userService.findByNickname(getUsernameFromPrincipal()));
        return "/users/adminPage";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public String index(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));
        return "/users/index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users/{nickname}")
    public String show(@PathVariable("nickname") String nickname,
                       Model model) {
        model.addAttribute("user", userService.findByNickname(nickname));
        model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));
        return "/users/show";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user,
                          @ModelAttribute("role") Role role) {
        return "/users/new";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/users")
    public String create(
                         @ModelAttribute("user") @Valid User user,
                         @ModelAttribute("role") Role role,
                         BindingResult bindingResultUser) {
        if (bindingResultUser.hasErrors()) {
            return "/users/new";
        } else {
            userService.save(user, role);
            return "redirect:/admin/users";
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users/{nickname}/edit")
    public String edit(Model model,
                       @PathVariable("nickname") String nickname,
                       @ModelAttribute("Role") Role role) {
        model.addAttribute("user", userService.findByNickname(nickname));
        model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));

        return "/users/edit";
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/users/{nickname}")
    public String update(Model model,
                         @PathVariable("nickname") String nickname,
                         @ModelAttribute("user") @Valid User user,
                         @ModelAttribute("Role") @Valid Role role,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/users/edit";
        } else {
            userService.update(nickname, user, role);
            model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));
            return "redirect:/admin/users";
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/users/{nickname}")
    public String delete(Model model,
            @PathVariable("nickname") String nickname) {
        userService.delete(nickname);
        model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));

        return "redirect:/admin/users";
    }

    private String getUsernameFromPrincipal() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }

        return userName;
    }
}