package boot.dao;

import boot.model.Role;
import boot.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;
    private final RoleDao roleDao;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, RoleDao roleDao) {
        this.entityManager = entityManager;
        this.roleDao = roleDao;
    }

    private List<User> users = new ArrayList<>();

    @Transactional(readOnly = true)
    @Override
    public User findByNickname(String nickname) {
        List<User> userList = null;
        User searchedUser = null;
        try {
            entityManager.getTransaction().begin();
            userList = (List<User>) entityManager
                    .createQuery("SELECT u FROM User u WHERE u.nickname =: nickname", User.class)
                    .setParameter("nickname", nickname)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        if (userList != null && !userList.isEmpty()) {
            searchedUser = userList.get(0);
        }
        if (searchedUser != null &&
                (searchedUser.getAuthorities() == null || searchedUser.getAuthorities().isEmpty())) {
            List<Role> userRoles = findRolesByUserNicknameAndId(searchedUser.getNickname(),
                    searchedUser.getId());
            searchedUser.setRoles(userRoles);
        }

        return searchedUser;
    }

    @Override
    public void save(User user, Role role) {
        Role newUserRole = roleDao.findByRoleNameOrNull(role.getRoleName());
        try {
            entityManager.getTransaction().begin();
            if (newUserRole != null) {
                List<Role> newUserRoleList = new ArrayList<>();
                newUserRoleList.add(newUserRole);
                user.setRoles(newUserRoleList);
            }
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Transactional
    @Override
    public void update(String nickname, User user, Role role) {
        List<Role> newUserRoles = new ArrayList<>();
        User userToBeUpdated = findByNickname(nickname);
        List<Role> oldUserRoles = findRolesByUserNicknameAndId(userToBeUpdated.getNickname(),
                userToBeUpdated.getId());
        if (!role.getRoleName().equals("RESET_ROLES")) {
            if (oldUserRoles != null && !oldUserRoles.isEmpty()) {
                newUserRoles.addAll(oldUserRoles);
            }
            if (role.getRoleName().equals("ADMIN") || role.getRoleName().equals("USER")) {
                boolean isQueryWillExecute = true;
                if (oldUserRoles != null) {
                    for (Role userRole : oldUserRoles) {
                        if (userRole.getRoleName().equals(role.getRoleName())) {
                            isQueryWillExecute = false;
                            break;
                        }
                    }
                }
                if (isQueryWillExecute) {
                    Role newUserRole = roleDao.findByRoleNameOrNull(role.getRoleName());
                    if (newUserRole != null) {
                        newUserRoles.add(newUserRole);
                    }
                }
            }
        }
        try {
            entityManager.getTransaction().begin();
            userToBeUpdated.setFirstName(user.getFirstName());
            userToBeUpdated.setLastName(user.getLastName());
            userToBeUpdated.setNickname(user.getNickname());
            userToBeUpdated.setAge(user.getAge());
            userToBeUpdated.setEmail(user.getEmail());
            userToBeUpdated.setPassword(user.getPassword());
            userToBeUpdated.setRoles(newUserRoles);
            entityManager.merge(userToBeUpdated);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public List<Role> findRolesByUserNicknameAndId(String nickname, Long id) {
        List<Role> userRolesList = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            userRolesList = (List<Role>) entityManager
                    .createQuery("SELECT u.roles FROM User u WHERE u.nickname =: nickname " +
                            "AND u.id =: id")
                    .setParameter("nickname", nickname)
                    .setParameter("id", id)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        if (userRolesList.isEmpty()) {
            return null;
        }

        return userRolesList;
    }

    @Override
    public void delete(String nickname) {
        User user = findByNickname(nickname);
        if (user == null) {
            return;
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        users.clear();
        try {
            entityManager.getTransaction().begin();
            users = entityManager
                    .createQuery("SELECT u FROM User u", User.class)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

        return users;
    }
}