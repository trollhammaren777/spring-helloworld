package bm.crud.security.spring.controller;

import bm.crud.security.spring.model.User;
import bm.crud.security.spring.model.UserWrapper;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @GetMapping(path = {"/users", "/users{nickname}"})
    public String index(ModelMap model,
                        @PathVariable(required = false) String nickname,
                        @ModelAttribute("newUser") User user,
                        @ModelAttribute("newRole") Role role,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "templates/WEB-INF/pages/users/index";
        }
        UserWrapper userWrapper = new UserWrapper(userService.findAll());
        model.addAttribute("userWrapper", userWrapper);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));

        return "templates/WEB-INF/pages/users/index";
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(path = {"/users","/users/{nickname}"})
    public String update(Model model,
                         @PathVariable("nickname") String nickname,
                         @Valid @ModelAttribute("userWrapper") UserWrapper userWrapper,
                         @ModelAttribute("roleToBeUpdated") Role role,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "templates/WEB-INF/pages/users/index";
        } else {
            userService.update(nickname, userWrapper.getUserByNickname(nickname), role);
            model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));

            return "redirect:";
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping (path = {"/users","/users/{nickname}"})
    public String delete(Model model,
                         @PathVariable("nickname") String nickname) {
        userService.delete(nickname);
        model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));

        return "redirect:";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/users")
    public String create(
            @ModelAttribute("user") @Valid User user,
            @ModelAttribute("role") Role role,
            BindingResult bindingResultUser) {
        if (bindingResultUser.hasErrors()) {
            return "templates/WEB-INF/pages/users/index";
        } else {
            userService.save(user, role);
            return "redirect:users/";
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/user/{nickname}")
    public String show(@PathVariable("nickname") String nickname,
                       Model model) {
        model.addAttribute("user", userService.findByNickname(nickname));
        model.addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));

        return "templates/WEB-INF/pages/users/show";
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