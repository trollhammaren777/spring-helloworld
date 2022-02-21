package boot.service;

import boot.dao.UserDao;
import boot.model.Role;
import boot.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void save(User user, Role role) {
        userDao.save(user, role);
    }

    @Transactional
    @Override
    public void update(String nickname, User user, Role role) {
        userDao.update(nickname, user, role);
    }

    @Transactional
    @Override
    public void delete(String nickname) {
        userDao.delete(nickname);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByNickname(String nickname) {
        return userDao.findByNickname(nickname);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.findByNickname(s);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new org.springframework.security.core.userdetails.User(user.getNickname(), user.getPassword(),
                user.getAuthorities());
    }
}

