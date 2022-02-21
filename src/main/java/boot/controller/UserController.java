package boot.controller;

import boot.exceptions.NoSuchUserException;
import boot.model.User;
import boot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{nickname}")
    public ModelAndView showByNickname(@PathVariable("nickname") String nickname, ModelAndView modelAndView){
        modelAndView.setViewName("show");
        User user = userService.findByNickname(nickname);
        if (user == null) {
            throw new NoSuchUserException("There is no user with nickname = " + nickname);
        }
        modelAndView.getModelMap().addAttribute("loggedInUser", userService.findByNickname(getUsernamePrincipal()));
        modelAndView.getModelMap().addAttribute("loggedInUserGetRequest", user);
        return modelAndView;
    }

    private String getUsernamePrincipal() {
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