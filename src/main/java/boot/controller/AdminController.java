package boot.controller;

import boot.model.User;
import boot.exceptions.NoSuchUserException;
import boot.service.UserService;
import boot.model.Role;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class  AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = {"/users"})
    public ModelAndView index(ModelAndView modelAndView,
                              BindingResult bindingResult) {
        modelAndView.setViewName("index");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        modelAndView.getModelMap().addAttribute("loggedInUser", userService.findByNickname(getUsernameFromPrincipal()));
        return modelAndView;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getUser/{nickname}", produces = "application/json")
    public User getUser(@PathVariable("nickname") String nickname) {
        User user = userService.findByNickname(nickname);
        if (user == null) {
            throw new NoSuchUserException("There is no user with nickname = " + nickname);
        }
        return user;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(path = {"/users", "/users/{nickname}"})
    public void update(@RequestBody ObjectNode payload,
                       @PathVariable("nickname") String nickname) {
        User tempUser = getTempUserAndRoleDataFromJson(payload);
        userService.update(nickname, tempUser, tempUser.getRoles().get(0));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = {"/users","/users/{nickname}"})
    public void delete(@PathVariable("nickname") String nickname) {
        User user = userService.findByNickname(nickname);
        if (user == null) {
            throw new NoSuchUserException("\"There is no user with nickname = " + nickname);
        } else {
            userService.delete(nickname);
        }
    }

    private User getTempUserAndRoleDataFromJson(ObjectNode payload) {
        User user = new User();
        if (payload.get("id") != null) {
            user.setId(Long.parseLong(payload.get("id").asText()));
        }
        user.setFirstName(payload.get("firstName").asText());
        user.setLastName(payload.get("lastName").asText());
        user.setNickname(payload.get("nickname").asText());
        user.setAge(Integer.parseInt(payload.get("age").asText()));
        user.setEmail(payload.get("email").asText());
        user.setPassword(payload.get("password").asText());
        Role role = new Role();
        if (payload.get("userRole") == null) {
            role.setRoleName("The user did not specify a role / did not update the role");
        } else {
            String roleName = payload.get("userRole").asText();
            role.setRoleName(roleName);
        }
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        user.setRoles(roleList);
        return user;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(path = {"/users"})
    public void create(@RequestBody ObjectNode payload, BindingResult bindingResultUser) {
        User newUser = getTempUserAndRoleDataFromJson(payload);
        if (!bindingResultUser.hasErrors()) {
            userService.save(newUser, newUser.getRoles().get(0));
        }
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

    @Secured("ROLE_ADMIN")
    @GetMapping(path = {"/users/all"}, produces = "application/json")
    public List<User> all() {
        return userService.findAll();
    }
}