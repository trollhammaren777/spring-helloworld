package bm.crud.security.spring.dao;

import bm.crud.security.spring.model.Role;
import bm.crud.security.spring.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao {
    void save(User user, Role role);
    void update(String nickname, User user, Role role);
    void delete(String nickname);
    @Transactional(readOnly = true)
    List<User> findAll();
    @Transactional(readOnly = true)
    User findByNickname(String nickname);
    @Transactional(readOnly = true)
    List<Role> findRolesByUserNicknameAndId(String nickname, Long id);
}
