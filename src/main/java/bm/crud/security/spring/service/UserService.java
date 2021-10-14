package bm.crud.security.spring.service;

import bm.crud.security.spring.model.Role;
import bm.crud.security.spring.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService { // CRUD:
    void save(User user, Role role); //create
    void update(String nickname, User user, Role role); //update
    void delete(String nickname); // delete
    @Transactional(readOnly = true)
    User findByNickname(String nickname); // read
    List<User> findAll();
}
