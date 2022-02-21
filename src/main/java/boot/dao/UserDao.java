package boot.dao;

import boot.model.Role;
import boot.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
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